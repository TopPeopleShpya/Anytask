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
import com.company.anytask.GsonSingleton;
import com.company.anytask.R;
import com.company.anytask.SchoolFragment;
import com.company.anytask.TasksFragment;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Course;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FillOrganizationCoursesTask extends AsyncTask<Integer, Void, List<Course>> {
    private SchoolFragment fragment;
    private FragmentManager fragmentManager;
    private AnytaskApiClient api;
    private SwipeRefreshLayout rootView;
    private Integer organizationId;
    private Context context;

    public FillOrganizationCoursesTask(SchoolFragment fragment,
                                       FragmentManager fragmentManager, AnytaskApiClient api,
                                       SwipeRefreshLayout rootView) {
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
        this.api = api;
        context = fragment.getContext();
        this.rootView = rootView;
    }

    @Override
    protected void onPreExecute() {
        if (fragment.getCourses() == null)
            rootView.setRefreshing(true);
    }

    @Override
    protected List<Course> doInBackground(Integer... params) {
        if (fragment.getCourses() == null) {
            organizationId = params[0];
            fragment.setCourses(api.organizationsApi().getCourses(organizationId));
        }
        return fragment.getCourses();
    }

    @Override
    protected void onPostExecute(final List<Course> courses) {
        if (courses == null) {
            new FillOrganizationCoursesTask(fragment, fragmentManager, api, rootView).execute(organizationId);
            return;
        }

        final ListView coursesListView = (ListView) rootView.findViewById(R.id.courses_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(fragment.getActivity(),
                R.layout.list_item_organization,
                R.id.list_item_school_textview,
                new ArrayList<String>()
        );
        coursesListView.setAdapter(adapter);

        final List<String> courseNames = new ArrayList<>();
        for (Course course : courses) {
            courseNames.add(course.name);
        }
        adapter.clear();
        adapter.addAll(courseNames);

        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TasksFragment tasksFragment = new TasksFragment();
                Course course = courses.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(context.getString(R.string.bundle_course), GsonSingleton.getGson().toJson(course));
                tasksFragment.setArguments(bundle);
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_frame, tasksFragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        if (rootView.isRefreshing())
            rootView.setRefreshing(false);
    }
}