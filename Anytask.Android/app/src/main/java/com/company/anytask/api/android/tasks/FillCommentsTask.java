package com.company.anytask.api.android.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.company.anytask.CellItem;
import com.company.anytask.CommentActivityFragment;
import com.company.anytask.R;
import com.company.anytask.TasksFragment;
import com.company.anytask.api.client.AnytaskApiClient;
import com.company.anytask.models.Comment;
import com.company.anytask.models.Score;

import java.util.ArrayList;
import java.util.List;

public class FillCommentsTask extends AsyncTask<CellItem, Void, Score> {
    private static final String TAG = FillCommentsTask.class.getSimpleName();
    private CommentActivityFragment fragment;
    private FragmentManager fragmentManager;
    private AnytaskApiClient api;
    private View rootView;
    private Integer courseId;
    private Context context;

    public FillCommentsTask(CommentActivityFragment fragment, FragmentManager fragmentManager,
                            AnytaskApiClient api, View rootView) {
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
        this.api = api;
        context = fragment.getContext();
        this.rootView = rootView;
    }

    @Override
    protected Score doInBackground(CellItem... params) {
        CellItem item = params[0];
        return api.scoresApi().getScore(item.userId, item.taskId);
    }

    @Override
    protected void onPostExecute(Score score) {
        List<String> comments = new ArrayList<>();
        for (Comment comment : score.comments) {
            comments.add(comment.text);
        }
        ListView commentsListView = (ListView) rootView.findViewById(R.id.comments_list);
        commentsListView.setAdapter(new ArrayAdapter<String>(context, R.layout.list_item_organization, comments));
    }
}
