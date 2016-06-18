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
import android.widget.SimpleAdapter;

import com.company.anytask.MyCoursesFragment;
import com.company.anytask.R;
import com.company.anytask.TasksFragment;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Course;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FillUserCoursesTask extends AsyncTask<String, Void, List<Course>> {
    private MyCoursesFragment fragment;
    private FragmentManager fragmentManager;
    private AnytaskApiClient api;
    private SwipeRefreshLayout rootView;
    private String userId;
    private Context context;

    public FillUserCoursesTask(MyCoursesFragment fragment, FragmentManager fragmentManager,
                               AnytaskApiClient api, SwipeRefreshLayout rootView) {
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
        this.api = api;
        context = fragment.getContext();
        this.rootView = rootView;
    }

    @Override
    protected List<Course> doInBackground(String... params) {
        if (fragment.getCourses() == null) {
            userId = params[0];
            fragment.setCourses(api.usersApi().getUserCourses(userId));
        }
        return fragment.getCourses();
    }

    @Override
    protected void onPostExecute(final List<Course> courses) {
        if (courses == null) {
            new FillUserCoursesTask(fragment, fragmentManager, api, rootView).execute(userId);
            return;
        }

        final List<String> courseNames = new ArrayList<>();
        final ListView coursesListView = (ListView) rootView.findViewById(R.id.my_courses_list);

        String[] from = new String[] { "name", "organization" };
        int[] to = new int[] { R.id.list_item_course_name, R.id.list_item_course_organization };

        List<HashMap<String, Object>> rows = new ArrayList<>();

        for (final Course course : courses) {
            HashMap<String, Object> rowItem = new HashMap<String, Object>() {{
                put("name", course.name);
                put("organization", course.organization.name);
            }};
            rows.add(rowItem);
            courseNames.add(course.name);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, rows, R.layout.list_item_my_course, from, to);
        coursesListView.setAdapter(adapter);

        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TasksFragment tasksFragment = new TasksFragment();
                Course course = courses.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(context.getString(R.string.bundle_course), new Gson().toJson(course));
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