package com.company.anytask.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.anytask.R;
import com.company.anytask.api.android.tasks.FillCommentsTask;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.custom_layouts.CellItem;
import com.company.anytask.models.Status;

/**
 * A placeholder fragment containing a simple view.
 */
public class CommentActivityFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        String userId = intent.getStringExtra("userId");
        int taskId = intent.getIntExtra("taskId", 0);
        CellItem item = new CellItem(userId, taskId, Status.BLANK, null);
        View view =  inflater.inflate(R.layout.fragment_comment, container, false);
        new FillCommentsTask(this, getFragmentManager(), new AnytaskApiClient(), view).execute(item);
        return view;
    }
}
