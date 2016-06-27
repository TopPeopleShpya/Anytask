package com.company.anytask.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.anytask.R;
import com.company.anytask.api.android.tasks.FillDeadlinesTask;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Task;
import com.company.anytask.utils.Config;

import java.util.List;

public class MyDeadlinesFragment extends Fragment {
    private Bundle args;
    private List<Task> tasks;

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
                tasks = null;
                getFillDeadlinesTask(rootView).execute(Config.USER_ID);
            }
        });
        rootView.post(new Runnable() {
            @Override
            public void run() {
                if (tasks == null)
                    rootView.setRefreshing(true);
                getFillDeadlinesTask(rootView).execute(Config.USER_ID);
            }
        });
        return rootView;
    }

    private FillDeadlinesTask getFillDeadlinesTask(SwipeRefreshLayout rootView) {
        return new FillDeadlinesTask(this,
                getFragmentManager(), new AnytaskApiClient(),
                rootView);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
