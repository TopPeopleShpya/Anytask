package com.company.anytask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.company.anytask.api.android.tasks.FillOrganizationsTask;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Organization;

import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private List<Organization> organizations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final SwipeRefreshLayout rootView = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_main, container, false);

        final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                organizations = null;
                getFillOrganizationsTask(rootView).execute();
            }
        };
        rootView.setOnRefreshListener(onRefreshListener);

        rootView.post(new Runnable() {
            @Override
            public void run() {
                if (organizations == null)
                    rootView.setRefreshing(true);
                getFillOrganizationsTask(rootView).execute();
            }
        });

        return rootView;
    }

    private FillOrganizationsTask getFillOrganizationsTask(SwipeRefreshLayout rootView) {
        return new FillOrganizationsTask(this,
                getFragmentManager(), new AnytaskApiClient(), rootView);
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
}
