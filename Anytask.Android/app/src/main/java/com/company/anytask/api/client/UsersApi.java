package com.company.anytask.api.client;

import com.company.anytask.api.interfaces.IUsersApi;
import com.company.anytask.models.Course;
import com.company.anytask.models.Task;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsersApi extends BaseApi implements IUsersApi {
    private static final String TAG = UsersApi.class.getSimpleName();

    UsersApi() {
        super("users");
    }

    @Override
    public List<Course> getUserCourses(String userId) {
        Reader reader = getUrl(combineUrls(url, userId, "StudyingCourses"));
        return reader == null
                ? new ArrayList<Course>()
                : Arrays.asList(gson.fromJson(reader, Course[].class));
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
