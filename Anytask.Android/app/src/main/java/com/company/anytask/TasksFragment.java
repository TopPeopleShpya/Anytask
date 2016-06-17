package com.company.anytask;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.company.anytask.models.Course;
import com.company.anytask.models.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {
    private Bundle args;

    @Override
    public void setArguments(Bundle args) {
        this.args = args;
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Course course = new Gson().fromJson(args.getString(getString(R.string.bundle_course)), Course.class);

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
