package com.company.anytask.api.interfaces;

import com.company.anytask.models.Course;
import com.company.anytask.models.CourseTasks;
import com.company.anytask.models.Task;
import com.company.anytask.models.User;

import java.util.Collection;
import java.util.List;

public interface ICoursesApi {
    List<Course> getCourses();
    Course getCourse(int id);

    List<Task> getTasks(int courseId);
    CourseTasks getCourseTasks(int courseId);
    List<User> getStudents(int courseId);
}
