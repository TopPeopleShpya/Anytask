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
import com.company.anytask.api.client.OrganizationsApi;
import com.company.anytask.models.Organization;

import java.util.*;

public class MainFragment extends Fragment {
    private ArrayAdapter<String> adapter;
    private ListView schoolList;
    public class FetchDataTask<T> extends AsyncTask<String, Void, Collection<Organization>> {
        private Class clazz;

        public FetchDataTask() {}

        public FetchDataTask(Class clazz) {

            this.clazz = clazz;
        }
        @Override
        protected Collection<Organization> doInBackground(String... params) {
            return new OrganizationsApi().getOrganizations();
        }

        @Override
        protected void onPostExecute(Collection<Organization> organizations) {
            final List<String> schools = new ArrayList<>();
            for (Organization organization : organizations) {
                schools.add(organization.name);
            }
            adapter.clear();
            adapter.addAll(schools);

            schoolList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String schoolName = schools.get(position);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new SchoolFragment(schoolName))
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        schoolList = (ListView) rootView.findViewById(R.id.schools_list);
        adapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item_school,
                R.id.list_item_school_textview,
                new ArrayList<String>()
        );
        schoolList.setAdapter(adapter);
        new FetchDataTask<>().execute();
        return rootView;
    }
}
