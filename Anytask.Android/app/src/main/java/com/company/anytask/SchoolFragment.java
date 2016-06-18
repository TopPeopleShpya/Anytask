package com.company.anytask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.company.anytask.api.android.tasks.FillOrganizationCoursesTask;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Course;
import com.company.anytask.models.Organization;
import com.google.gson.Gson;

import java.util.List;

public class SchoolFragment extends Fragment {
    private Bundle args;
    private List<Course> courses;

    @Override
    public void setArguments(Bundle args) {
        this.args = args;
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final Organization organization = new Gson()
                .fromJson(args.getString(getString(R.string.bundle_organization)), Organization.class);

        final SwipeRefreshLayout rootView = (SwipeRefreshLayout) inflater
                .inflate(R.layout.fragment_organization, container, false);
        TextView organizationNameTextView = (TextView) rootView.findViewById(R.id.organization_name);
        organizationNameTextView.setText(organization.name);

        final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                courses = null;
                getFillOrganizationCoursesTask(rootView).execute(organization.id);
            }
        };
        rootView.setOnRefreshListener(onRefreshListener);

        rootView.post(new Runnable() {
            @Override
            public void run() {
                if (courses == null)
                    rootView.setRefreshing(true);
                getFillOrganizationCoursesTask(rootView).execute(organization.id);
            }
        });

        return rootView;
    }

    private FillOrganizationCoursesTask getFillOrganizationCoursesTask(SwipeRefreshLayout rootView) {
        return new FillOrganizationCoursesTask(this,
                getFragmentManager(), new AnytaskApiClient(),
                rootView);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
