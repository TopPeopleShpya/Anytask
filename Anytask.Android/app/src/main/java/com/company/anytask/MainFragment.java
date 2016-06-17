package com.company.anytask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.company.anytask.api.android.tasks.FillOrganizationsTask;
import com.company.anytask.api.client.AnytaskApiClient;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final SwipeRefreshLayout rootView = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_main, container, false);

        final ListView schoolListView = (ListView) rootView.findViewById(R.id.schools_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item_organization,
                R.id.list_item_school_textview,
                new ArrayList<String>()
        );

        schoolListView.setAdapter(adapter);

        final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FillOrganizationsTask(adapter, schoolListView,
                        getFragmentManager(), new AnytaskApiClient(),
                        getContext(), rootView).execute();
            }
        };
        rootView.setOnRefreshListener(onRefreshListener);

        rootView.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: " + rootView.getId());
                rootView.setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });

        return rootView;
    }
}
