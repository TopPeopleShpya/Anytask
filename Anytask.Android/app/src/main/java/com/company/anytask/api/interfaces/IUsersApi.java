package com.company.anytask.api.interfaces;

import com.company.anytask.models.Course;

import java.util.List;

public interface IUsersApi {
    List<Course> getUserCourses(String userId);
}
