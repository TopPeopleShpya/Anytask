package com.company.anytask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final List<String> schools = Arrays.asList(
            "Высшая Школа Экономики",
            "Московский Физико-Технический Институт",
            "Школа Анализа Данных",
            "Школа Программирования Яндекса"
        );

        ListView ul = (ListView) rootView.findViewById(R.id.schools_list);
        ul.setAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.list_item_school,
                R.id.list_item_school_textview,
                new ArrayList<>(schools)
        ));
        ul.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String schoolName = schools.get(position);
                getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new SchoolFragment(schoolName))
                    .addToBackStack(null)
                    .commit();
            }
        });
        return rootView;
    }
}
