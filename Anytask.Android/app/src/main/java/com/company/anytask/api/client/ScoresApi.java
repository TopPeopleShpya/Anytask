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
    public Score getScore(User student, Task task) {
        Reader reader = getUrl(url + "?userId=" + student.id + "&taskId=" + task.id);
        return reader == null
                ? null
                : gson.fromJson(reader, Score.class);
    }
}
