package com.company.anytask;

import com.company.anytask.models.Status;

public class CellItem {
    public String userId;
    public int taskId;
    public Status status;
    public String text;

    public CellItem(String userId, int taskId, Status status, String text) {
        this.userId = userId;
        this.taskId = taskId;
        this.status = status;
        this.text = text;
    }
}
