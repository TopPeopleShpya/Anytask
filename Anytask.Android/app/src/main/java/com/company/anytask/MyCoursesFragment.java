package com.company.anytask;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.anytask.api.android.tasks.FillUserCoursesTask;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Course;

import java.util.List;

public class MyCoursesFragment extends Fragment {
    private Bundle args;
    private List<Course> courses;

    @Override
    public void setArguments(Bundle args) {
        this.args = args;
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final SwipeRefreshLayout rootView = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_my_courses, container, false);
        rootView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                courses = null;
                getFillOrganizationCoursesTask(rootView).execute(Config.USER_ID);
            }
        });
        rootView.post(new Runnable() {
            @Override
            public void run() {
                if (courses == null)
                    rootView.setRefreshing(true);
                getFillOrganizationCoursesTask(rootView).execute(Config.USER_ID);
            }
        });
        return rootView;
    }

    private FillUserCoursesTask getFillOrganizationCoursesTask(SwipeRefreshLayout rootView) {
        return new FillUserCoursesTask(this,
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
