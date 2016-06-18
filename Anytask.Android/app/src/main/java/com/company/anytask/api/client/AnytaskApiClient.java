package com.company.anytask.api.client;

import com.company.anytask.api.interfaces.*;

public class AnytaskApiClient implements IAnytaskApiClient {
    private IOrganizationsApi organizationsApi = new OrganizationsApi();
    private ICoursesApi coursesApi = new CoursesApi();
    private IScoresApi scoresApi = new ScoresApi();
    private ITasksApi tasksApi;

    @Override
    public ICoursesApi coursesApi() {
        return coursesApi;
    }

    @Override
    public IOrganizationsApi organizationsApi() {
        return organizationsApi;
    }

    @Override
    public IScoresApi scoresApi() {
        return scoresApi;
    }

    @Override
    public ITasksApi tasksApi() {
        return tasksApi;
    }
}
