package com.company.anytask.api.client;

import android.util.Log;
import com.company.anytask.api.interfaces.ITasksApi;

public class TasksApi extends BaseApi implements ITasksApi {
    private static final String TAG = TasksApi.class.getSimpleName();

    TasksApi() {
        super("tasks");
    }
}
