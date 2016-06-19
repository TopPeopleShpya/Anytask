package com.company.anytask.api.client;

import android.util.Log;

import com.company.anytask.api.interfaces.ITasksApi;
import com.company.anytask.api.interfaces.IUsersApi;
import com.company.anytask.models.Course;
import com.company.anytask.models.Task;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsersApi extends BaseApi implements IUsersApi {
    private static final String TAG = TasksApi.class.getSimpleName();

    UsersApi() {
        super("users");
    }

    @Override
    public List<Course> getUserCourses(String userId) {
        try {
            Reader reader = getUrl(combineUrls(url, userId, "StudyingCourses"));
            return Arrays.asList(gson.fromJson(reader, Course[].class));
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Task> getUserTasks(String userId) {
        List<Course> courses = getUserCourses(userId);
        List<Task> tasks = new ArrayList<>();
        CoursesApi coursesApi = new CoursesApi();
        for (Course course : courses)
            tasks.addAll(coursesApi.getTasks(course.id));
        return tasks;
    }
}
