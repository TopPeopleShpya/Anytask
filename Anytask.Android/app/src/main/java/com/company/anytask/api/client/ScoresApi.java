package com.company.anytask.api.client;

import com.company.anytask.api.interfaces.IScoresApi;
import com.company.anytask.models.Course;
import com.company.anytask.models.Score;
import com.company.anytask.models.Task;
import com.company.anytask.models.User;

import java.io.Reader;
import java.util.Arrays;

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
}
