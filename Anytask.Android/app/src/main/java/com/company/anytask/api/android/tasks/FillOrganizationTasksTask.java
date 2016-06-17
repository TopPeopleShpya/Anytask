package com.company.anytask.api.android.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.company.anytask.R;
import com.company.anytask.TasksFragment;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FillOrganizationTasksTask extends AsyncTask<Integer, Void, Collection<Course>> {
    private ArrayAdapter<String> adapter;
    private ListView coursesListView;
    private FragmentManager fragmentManager;
    private AnytaskApiClient api;
    private Context context;
    private SwipeRefreshLayout rootView;

    public FillOrganizationTasksTask(ArrayAdapter<String> adapter, ListView coursesListView,
                                     FragmentManager fragmentManager, AnytaskApiClient api,
                                     Context context, SwipeRefreshLayout rootView) {

        this.adapter = adapter;
        this.coursesListView = coursesListView;
        this.fragmentManager = fragmentManager;
        this.api = api;
        this.context = context;
        this.rootView = rootView;
    }

    @Override
    protected Collection<Course> doInBackground(Integer... params) {
        return api.organizationsApi().getCourses(params[0]);
    }

    @Override
    protected void onPostExecute(Collection<Course> courses) {
        if (courses == null) {
            this.execute();
            return;
        }
        final List<String> courseNames = new ArrayList<>();
        for (Course course : courses) {
            courseNames.add(course.name);
        }
        adapter.clear();
        adapter.addAll(courseNames);


        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_frame, new TasksFragment())
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        if (rootView.isRefreshing())
            rootView.setRefreshing(false);
    }
}