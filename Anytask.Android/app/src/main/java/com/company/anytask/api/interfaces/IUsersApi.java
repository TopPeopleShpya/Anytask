package com.company.anytask.api.interfaces;

import com.company.anytask.models.Course;
import com.company.anytask.models.Task;

import java.util.List;

public interface IUsersApi {
    List<Course> getUserCourses(String userId);
    List<Task> getUserTasks(String userId);
}
