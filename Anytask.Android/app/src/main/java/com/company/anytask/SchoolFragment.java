package com.company.anytask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.company.anytask.api.android.tasks.FillOrganizationTasksTask;
import com.company.anytask.api.client.AnytaskApiClient;

import java.util.ArrayList;

public class SchoolFragment extends Fragment {
    private Bundle args;

    @Override
    public void setArguments(Bundle args) {
        this.args = args;
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String name = args.getString(getContext().getString(R.string.bundle_organization_name));
        Integer organizationId = args.getInt(getContext().getString(R.string.bundle_organization_id));

        View rootView = inflater.inflate(R.layout.fragment_organization, container, false);
        TextView organizationNameTextView = (TextView) rootView.findViewById(R.id.organization_name);
        organizationNameTextView.setText(name);

        ListView coursesListView = (ListView) rootView.findViewById(R.id.courses_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item_school,
                R.id.list_item_school_textview,
                new ArrayList<String>()
        );
        coursesListView.setAdapter(adapter);
        new FillOrganizationTasksTask(adapter, coursesListView,
                getFragmentManager(), new AnytaskApiClient(),
                getContext()).execute(organizationId);
        return rootView;
    }
}
