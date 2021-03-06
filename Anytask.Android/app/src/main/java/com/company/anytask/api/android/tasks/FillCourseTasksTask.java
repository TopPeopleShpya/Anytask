package com.company.anytask.api.android.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.company.anytask.custom_layouts.CellItem;
import com.company.anytask.custom_layouts.FixedHeaderTableLayout;
import com.company.anytask.R;
import com.company.anytask.fragments.TasksFragment;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.CourseTasks;
import com.company.anytask.models.Score;
import com.company.anytask.models.Task;
import com.company.anytask.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FillCourseTasksTask extends AsyncTask<Integer, Void, Void> {
    private static final String TAG = FillCourseTasksTask.class.getSimpleName();
    private TasksFragment fragment;
    private FragmentManager fragmentManager;
    private AnytaskApiClient api;
    private View rootView;
    private Integer courseId;
    private Context context;

    public FillCourseTasksTask(TasksFragment fragment,
                               FragmentManager fragmentManager, AnytaskApiClient api,
                               View rootView) {
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
        this.api = api;
        context = fragment.getContext();
        this.rootView = rootView;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        courseId = params[0];
        CourseTasks courseTasks = api.coursesApi().getCourseTasks(courseId);
        if (fragment.getTasks() == null) {
            fragment.setTasks(courseTasks.tasks);
        }
        if (fragment.getStudents() == null) {
            fragment.setStudents(courseTasks.students);
        }
        if (fragment.getTasks() != null && fragment.getStudents() != null) {
            if (fragment.getScores() == null) {
                HashMap<User, HashMap<Task, Score>> scores = new HashMap<>();
                for (User student : fragment.getStudents()) {
                    scores.put(student, new HashMap<Task, Score>());
                    for (Task task : fragment.getTasks()) {
                        Log.i(TAG, "getting score for " + task.name + " and " + student.name);
                        scores.get(student).put(task, courseTasks.userTasks.get(student.id).get(task.id));
                    }
                }
                fragment.setScores(scores);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void arg) {
        List<User> students = fragment.getStudents();
        List<Task> tasks = fragment.getTasks();
        HashMap<User, HashMap<Task, Score>> scores = fragment.getScores();

        if (tasks == null || students == null || scores == null) {
            new FillCourseTasksTask(fragment, fragmentManager, api, rootView).execute(courseId);
            return;
        }

        int tasksCount = tasks.size();
        int studentsCount = students.size();
        if (tasksCount == 0 && studentsCount == 0) {
            return;
        }

        CellItem[] horizontalHeaders = new CellItem[tasksCount];
        for (int i = 0; i < tasksCount; i++) {
            horizontalHeaders[i] = new CellItem(null, tasks.get(i).id, com.company.anytask.models.Status.BLANK, tasks.get(i).name);
        }


        CellItem[] verticalHeaders = new CellItem[studentsCount];
        for (int i = 0; i < studentsCount; i++) {
            verticalHeaders[i] = new CellItem(students.get(i).id, 0, com.company.anytask.models.Status.BLANK, students.get(i).name);
        }

        ArrayList<ArrayList<CellItem>> items = new ArrayList<>();
        for (User student : students) {
            ArrayList<CellItem> row = new ArrayList<>();
            items.add(row);
            for (Task task : tasks) {
                Score score = scores.get(student).get(task);
                com.company.anytask.models.Status status;

                if (score == null)
                    status = com.company.anytask.models.Status.NEW;
                else if (score.value == task.maxScore)
                    status = com.company.anytask.models.Status.COMPLETE;
                else
                    status = com.company.anytask.models.Status.ON_REVIEW;

                row.add(new CellItem(student.id, task.id, status, score == null
                        ? "-"
                        : score.value.toString()));
            }
        }
        FixedHeaderTableLayout layout = (FixedHeaderTableLayout) rootView.findViewById(R.id.table);

        layout.setTableContent(horizontalHeaders, verticalHeaders, items);
    }
}