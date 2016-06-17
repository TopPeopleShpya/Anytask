package com.company.anytask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.company.anytask.api.android.tasks.FillOrganizationsTask;
import com.company.anytask.api.client.AnytaskApiClient;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView schoolListView = (ListView) rootView.findViewById(R.id.schools_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item_school,
                R.id.list_item_school_textview,
                new ArrayList<String>()
        );

        schoolListView.setAdapter(adapter);
        new FillOrganizationsTask(adapter, schoolListView,
                getFragmentManager(), new AnytaskApiClient(),
                getContext()).execute();
        return rootView;
    }
}
