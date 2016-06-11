package com.company.anytask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchoolFragment extends Fragment {
    private String name;

    public SchoolFragment() {
        super();
    }

    public SchoolFragment(String name) {
        this();
        this.name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_school, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.school_name);
        tv.setText(name);

        List<String> courses = Arrays.asList(
            "Алгоритмы и структуры данных",
            "Python",
            "Базы данных (2016)"
        );

        ListView ul = (ListView) rootView.findViewById(R.id.courses_list);
        ul.setAdapter(new ArrayAdapter<>(getActivity(),
            R.layout.list_item_school,
            R.id.list_item_school_textview,
            new ArrayList<>(courses)
        ));
        ul.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new TasksFragment())
                    .addToBackStack(null)
                    .commit();
            }
        });

        return rootView;
    }
}
