package com.company.anytask.api.android.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.company.anytask.MainFragment;
import com.company.anytask.R;
import com.company.anytask.SchoolFragment;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Organization;

import java.util.ArrayList;
import java.util.List;

public class FillOrganizationsTask extends AsyncTask<Void, Void, List<Organization>> {
    private static final String TAG = FillOrganizationsTask.class.getSimpleName();
    private MainFragment fragment;
    private FragmentManager fragmentManager;
    private AnytaskApiClient api;
    private Context context;
    private SwipeRefreshLayout rootView;

    public FillOrganizationsTask(MainFragment fragment, FragmentManager fragmentManager, AnytaskApiClient api,
                                 Context context, SwipeRefreshLayout rootView) {
        this.fragment = fragment;

        this.fragmentManager = fragmentManager;
        this.api = api;
        this.context = context;
        this.rootView = rootView;
    }

    @Override
    protected List<Organization> doInBackground(Void... params) {
        if (fragment.getOrganizations() == null)
            fragment.setOrganizations(api.organizationsApi().getOrganizations());
        return fragment.getOrganizations();
    }

    @Override
    protected void onPostExecute(final List<Organization> organizations) {
        if (organizations == null) {
            new FillOrganizationsTask(fragment, fragmentManager, api, context, rootView).execute();
            return;
        }

        ListView schoolListView = (ListView) rootView.findViewById(R.id.schools_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(fragment.getActivity(),
                R.layout.list_item_organization,
                R.id.list_item_school_textview,
                new ArrayList<String>()
        );

        schoolListView.setAdapter(adapter);


        final List<String> organizationNames = new ArrayList<>();
        for (Organization organization : organizations) {
            organizationNames.add(organization.name);
        }
        adapter.clear();
        adapter.addAll(organizationNames);

        schoolListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Organization organization = organizations.get(position);
                String organizationName = organization.name;
                SchoolFragment schoolFragment = new SchoolFragment();
                Bundle bundle = new Bundle();
                bundle.putString(context.getString(R.string.bundle_organization_name), organizationName);
                bundle.putInt(context.getString(R.string.bundle_organization_id), organization.id);
                schoolFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, schoolFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });

        if (rootView.isRefreshing())
            rootView.setRefreshing(false);
    }
}