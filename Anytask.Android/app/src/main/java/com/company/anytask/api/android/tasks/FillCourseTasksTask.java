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
import com.company.anytask.R;
import com.company.anytask.TasksFragment;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Course;
import com.company.anytask.models.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FillCourseTasksTask extends AsyncTask<Integer, Void, List<Task>> {
    private TasksFragment fragment;
    private FragmentManager fragmentManager;
    private AnytaskApiClient api;
    private SwipeRefreshLayout rootView;
    private Integer courseId;
    private Context context;

    public FillCourseTasksTask(TasksFragment fragment,
                               FragmentManager fragmentManager, AnytaskApiClient api,
                               SwipeRefreshLayout rootView) {
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
        this.api = api;
        context = fragment.getContext();
        this.rootView = rootView;
    }

    @Override
    protected List<Task> doInBackground(Integer... params) {
        if (fragment.getTasks() == null) {
            courseId = params[0];
            fragment.setTasks(api.coursesApi().getTasks(courseId));
        }
        return fragment.getTasks();
    }

    @Override
    protected void onPostExecute(final List<Task> tasks) {
        if (tasks == null) {
            new FillCourseTasksTask(fragment, fragmentManager, api, rootView).execute(courseId);
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
        for (Task task : tasks) {
            courseNames.add(task.name);
        }
        adapter.clear();
        adapter.addAll(courseNames);

        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TasksFragment tasksFragment = new TasksFragment();
                Task task = tasks.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(context.getString(R.string.bundle_task), new Gson().toJson(task));
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