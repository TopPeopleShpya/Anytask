package com.company.anytask.api.client;

import com.company.anytask.api.interfaces.*;

public class AnytaskApiClient implements IAnytaskApiClient {
    private IOrganizationsApi organizationsApi = new OrganizationsApi();
    private ICoursesApi coursesApi = new CoursesApi();
    private IUsersApi usersApi = new UsersApi();
    private IScoresApi scoresApi = new ScoresApi();
    private ITasksApi tasksApi = new TasksApi();

    @Override
    public ICoursesApi coursesApi() {
        return coursesApi;
    }

    @Override
    public IOrganizationsApi organizationsApi() {
        return organizationsApi;
    }

    @Override
    public IUsersApi usersApi() {
        return usersApi;
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
