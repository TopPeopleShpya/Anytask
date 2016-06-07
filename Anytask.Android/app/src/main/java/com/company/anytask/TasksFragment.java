package com.company.anytask;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TasksFragment extends Fragment {
    public TasksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        FixedHeaderTableLayout layout = (FixedHeaderTableLayout) rootView.findViewById(R.id.table);

        int columnsCount = 10;
        int rowsCount = 20;
        CellItem[] headers = new CellItem[columnsCount];
        headers[0] = new CellItem("         ");
        for (int i = 1; i < columnsCount; i++) {
            headers[i] = new CellItem("header" + i);
        }

        ArrayList<ArrayList<CellItem>> items = new ArrayList<>();
        for (int i = 0; i < rowsCount; i++) {
            items.add(new ArrayList<CellItem>());
            for (int j = 0; j < columnsCount; j++) {
                items.get(i).add(new CellItem(i + " " + j));
            }
        }

        layout.setTableContent(headers, items);
        return rootView;
    }
}
