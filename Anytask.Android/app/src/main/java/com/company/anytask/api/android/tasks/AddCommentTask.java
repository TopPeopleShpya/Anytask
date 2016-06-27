package com.company.anytask.api.android.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.company.anytask.custom_layouts.CellItem;
import com.company.anytask.fragments.CommentActivityFragment;
import com.company.anytask.R;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Comment;
import com.company.anytask.models.Score;

import java.util.ArrayList;
import java.util.List;

public class AddCommentTask extends AsyncTask<String, Void, Void> {
    private static final String TAG = FillCommentsTask.class.getSimpleName();
    private CommentActivityFragment fragment;
    private FragmentManager fragmentManager;
    private AnytaskApiClient api;
    private View rootView;
    private CellItem item;

    public AddCommentTask(CommentActivityFragment fragment, FragmentManager fragmentManager,
                            AnytaskApiClient api, View rootView, CellItem item) {
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
        this.api = api;
        this.rootView = rootView;
        this.item = item;
    }

    @Override
    protected Void doInBackground(String... params) {
        Comment comment = new Comment();
        comment.text = params[0];
        api.scoresApi().postComment(item.taskId, comment);
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        new FillCommentsTask(fragment, fragmentManager, api, rootView).execute(item);
    }
}
