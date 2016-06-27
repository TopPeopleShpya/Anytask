package com.company.anytask.api.android.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.company.anytask.fragments.MyDeadlinesFragment;
import com.company.anytask.R;
import com.company.anytask.fragments.TasksFragment;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FillDeadlinesTask extends AsyncTask<String, Void, List<Task>> {
    private MyDeadlinesFragment fragment;
    private FragmentManager fragmentManager;
    private AnytaskApiClient api;
    private SwipeRefreshLayout rootView;
    private String userId;
    private Context context;

    public FillDeadlinesTask(MyDeadlinesFragment fragment, FragmentManager fragmentManager,
                             AnytaskApiClient api, SwipeRefreshLayout rootView) {
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
        this.api = api;
        context = fragment.getContext();
        this.rootView = rootView;
    }

    @Override
    protected List<Task> doInBackground(String... params) {
        if (fragment.getTasks() == null) {
            userId = params[0];
            fragment.setTasks(api.usersApi().getUserTasks(userId));
        }
        return fragment.getTasks();
    }

    @Override
    protected void onPostExecute(final List<Task> tasks) {
        if (tasks == null) {
            new FillDeadlinesTask(fragment, fragmentManager, api, rootView).execute(userId);
            return;
        }

        final List<String> taskNames = new ArrayList<>();
        final ListView tasksListView = (ListView) rootView.findViewById(R.id.my_courses_list);

        String[] from = new String[] { "name", "course", "deadline" };
        int[] to = new int[] { R.id.list_item_task_name, R.id.list_item_task_course, R.id.list_item_task_deadline };

        List<HashMap<String, Object>> rows = new ArrayList<>();

        for (final Task task : tasks) {
            HashMap<String, Object> rowItem = new HashMap<String, Object>() {{
                put("name", task.name);
                put("course", task.course.name);
                put("deadline", task.deadline.toString());
            }};
            rows.add(rowItem);
            taskNames.add(task.name);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, rows, R.layout.list_item_deadline, from, to);
        tasksListView.setAdapter(adapter);

        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TasksFragment tasksFragment = new TasksFragment();
                Task task = tasks.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(context.getString(R.string.bundle_course), new Gson().toJson(task));
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