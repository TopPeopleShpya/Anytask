package com.company.anytask.api.interfaces;

import com.company.anytask.models.Comment;
import com.company.anytask.models.Score;
import com.company.anytask.models.Task;
import com.company.anytask.models.User;

public interface IScoresApi {
    Score getScore(String userId, int taskId);
    void postComment(int taskId, Comment comment);
}
