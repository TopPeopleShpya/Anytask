package com.company.anytask.api.client;

import com.company.anytask.api.interfaces.ICoursesApi;
import com.company.anytask.models.Course;

import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class CoursesApi extends BaseApi implements ICoursesApi {
    private static final String TAG = CoursesApi.class.getSimpleName();

    CoursesApi() {
        super("courses");
    }

    @Override
    public List<Course> getCourses() {
        Reader reader = getUrl(url);
        return reader == null
                ? null
                : Arrays.asList(gson.fromJson(reader, Course[].class));
    }

    @Override
    public Course getCourse(int id) {
        Reader reader = getUrl(url + id);
        return reader == null
                ? null
                : gson.fromJson(reader, Course.class);
    }
}