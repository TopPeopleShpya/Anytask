package com.company.anytask.api.client;

import com.company.anytask.api.interfaces.ITasksApi;
import com.company.anytask.models.Task;

import java.io.Reader;

public class TasksApi extends BaseApi implements ITasksApi {
    private static final String TAG = TasksApi.class.getSimpleName();

    TasksApi() {
        super("tasks");
    }

    @Override
    public Task getTask(int id) {
        Reader reader = getUrl(url + id);
        return reader == null
                ? null
                : gson.fromJson(reader, Task.class);
    }
}
