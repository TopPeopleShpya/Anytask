package com.company.anytask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
        final Integer organizationId = args.getInt(getContext().getString(R.string.bundle_organization_id));

        final SwipeRefreshLayout rootView = (SwipeRefreshLayout) inflater
                .inflate(R.layout.fragment_organization, container, false);
        TextView organizationNameTextView = (TextView) rootView.findViewById(R.id.organization_name);
        organizationNameTextView.setText(name);

        final ListView coursesListView = (ListView) rootView.findViewById(R.id.courses_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item_organization,
                R.id.list_item_school_textview,
                new ArrayList<String>()
        );
        coursesListView.setAdapter(adapter);
        final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FillOrganizationTasksTask(adapter, coursesListView,
                        getFragmentManager(), new AnytaskApiClient(),
                        getContext(), rootView).execute(organizationId);
            }
        };
        rootView.setOnRefreshListener(onRefreshListener);
        rootView.post(new Runnable() {
            @Override
            public void run() {
                rootView.setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });
        return rootView;
    }
}
