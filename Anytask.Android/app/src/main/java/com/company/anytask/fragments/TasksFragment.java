package com.company.anytask.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.company.anytask.R;
import com.company.anytask.api.android.tasks.FillCourseTasksTask;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Course;
import com.company.anytask.models.Score;
import com.company.anytask.models.Task;
import com.company.anytask.models.User;
import com.company.anytask.utils.GsonSingleton;

import java.util.HashMap;
import java.util.List;

public class TasksFragment extends Fragment {
    private Bundle args;
    private List<Task> tasks;
    private List<User> students;
    private HashMap<User, HashMap<Task, Score>> scores;

    @Override
    public void setArguments(Bundle args) {
        this.args = args;
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final Course course = GsonSingleton.getGson()
                .fromJson(args.getString(getString(R.string.bundle_course)), Course.class);

        final View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        ((TextView) rootView.findViewById(R.id.course_name)).setText(course.name);

        rootView.post(new Runnable() {
            @Override
            public void run() {
                getFillCourseTasksTask(rootView).execute(course.id);
            }
        });

        return rootView;
    }

    private FillCourseTasksTask getFillCourseTasksTask(View rootView) {
        return new FillCourseTasksTask(this, getFragmentManager(), new AnytaskApiClient(), rootView);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> users) {
        this.students = users;
    }

    public HashMap<User, HashMap<Task, Score>> getScores() {
        return scores;
    }

    public void setScores(HashMap<User, HashMap<Task, Score>> scores) {
        this.scores = scores;
    }
}
