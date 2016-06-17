package com.company.anytask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.company.anytask.api.android.tasks.FillOrganizationTasksTask;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Course;

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
        String name = args.getString(getContext().getString(R.string.bundle_organization_name));
        final Integer organizationId = args.getInt(getContext().getString(R.string.bundle_organization_id));

        final SwipeRefreshLayout rootView = (SwipeRefreshLayout) inflater
                .inflate(R.layout.fragment_organization, container, false);
        TextView organizationNameTextView = (TextView) rootView.findViewById(R.id.organization_name);
        organizationNameTextView.setText(name);

        final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                courses = null;
                getFillOrganizationTasksTask(rootView).execute(organizationId);
            }
        };
        rootView.setOnRefreshListener(onRefreshListener);
        
        rootView.post(new Runnable() {
            @Override
            public void run() {
                rootView.setRefreshing(true);
                getFillOrganizationTasksTask(rootView).execute(organizationId);
            }
        });
        return rootView;
    }

    private FillOrganizationTasksTask getFillOrganizationTasksTask(SwipeRefreshLayout rootView) {
        return new FillOrganizationTasksTask(this,
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
