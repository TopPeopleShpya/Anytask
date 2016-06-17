package com.company.anytask.api.interfaces;

import com.company.anytask.models.Course;

import java.util.Collection;
import java.util.List;

public interface ICoursesApi {
    List<Course> getCourses();
    Course getCourse(int id);
}
