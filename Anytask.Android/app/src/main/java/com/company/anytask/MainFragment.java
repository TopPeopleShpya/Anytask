package com.company.anytask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainFragment extends Fragment {
    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView ul = (ListView) rootView.findViewById(R.id.schools_list);
        ul.setAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.list_item_school,
                R.id.list_item_school_textview,
                new ArrayList<>(Arrays.asList(
                        "Высшая Школа Экономики",
                        "Московский Физико-Технический Институт",
                        "Школа Анализа Данных",
                        "Школа Программирования Яндекса"
                ))
        ));
        return rootView;
    }
}
