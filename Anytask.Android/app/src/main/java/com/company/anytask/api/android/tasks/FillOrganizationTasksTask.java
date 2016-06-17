package com.company.anytask.api.android.tasks;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.company.anytask.R;
import com.company.anytask.SchoolFragment;
import com.company.anytask.TasksFragment;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FillOrganizationTasksTask extends AsyncTask<Integer, Void, Collection<Course>> {
    private SchoolFragment fragment;
    private FragmentManager fragmentManager;
    private AnytaskApiClient api;
    private SwipeRefreshLayout rootView;

    public FillOrganizationTasksTask(SchoolFragment fragment,
                                     FragmentManager fragmentManager, AnytaskApiClient api,
                                     SwipeRefreshLayout rootView) {
        this.fragment = fragment;

        this.fragmentManager = fragmentManager;
        this.api = api;
        this.rootView = rootView;
    }

    @Override
    protected Collection<Course> doInBackground(Integer... params) {
        if (fragment.getCourses() == null)
            fragment.setCourses(api.organizationsApi().getCourses(params[0]));
        return fragment.getCourses();
    }

    @Override
    protected void onPostExecute(Collection<Course> courses) {
        if (courses == null) {
            this.execute();
            return;
        }

        if (fragment.isRemoving()) {
            if (rootView.isRefreshing())
                rootView.setRefreshing(false);
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