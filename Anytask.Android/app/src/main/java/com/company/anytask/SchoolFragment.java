package com.company.anytask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.company.anytask.api.client.OrganizationsApi;
import com.company.anytask.models.Course;
import com.company.anytask.models.Organization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchoolFragment extends Fragment {
    private TextView schoolName;
    private ListView coursesListView;

    public class FetchSchoolTask extends AsyncTask<Integer, Void, Organization> {

        @Override
        protected Organization doInBackground(Integer... params) {
            return new OrganizationsApi().getOrganization(params[0]);
        }

        @Override
        protected void onPostExecute(Organization organization) {
            if (organization == null)
                return;

            final Course[] courses = (Course[]) organization.courses.toArray();

            schoolName.setText(organization.name);

            List<String> courseNames = new ArrayList<>();
            for (Course course : courses) {
                courseNames.add(course.name);
            }

            coursesListView.setAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.list_item_school,
                R.id.list_item_school_textview,
                new ArrayList<>(courseNames)
            ));

            coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int courseId = courses[position].id;
                    getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new TasksFragment())
                        .addToBackStack(null)
                        .commit();
                }
            });
        }
    }

    public static SchoolFragment newInstance(int index) {
        SchoolFragment f = new SchoolFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_school, container, false);

        schoolName = (TextView) rootView.findViewById(R.id.school_name);
        coursesListView = (ListView) rootView.findViewById(R.id.courses_list);
        new FetchSchoolTask().execute(getArguments().getInt("index"));

        return rootView;
    }
}
