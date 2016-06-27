package com.company.anytask.api.client;

import com.company.anytask.api.interfaces.IScoresApi;
import com.company.anytask.models.Comment;
import com.company.anytask.models.Score;

import java.io.Reader;

public class ScoresApi extends BaseApi implements IScoresApi {
    ScoresApi() {
        super("scores");
    }

    @Override
    public Score getScore(String userId, int taskId) {
        Reader reader = getUrl(url + "?userId=" + userId + "&taskId=" + taskId);
        return reader == null
                ? null
                : gson.fromJson(reader, Score.class);
    }

    @Override
    public void postComment(int id, Comment comment) {
        postUrl(combineUrls(url, id, "PostComment"), gson.toJson(comment));
    }
}
