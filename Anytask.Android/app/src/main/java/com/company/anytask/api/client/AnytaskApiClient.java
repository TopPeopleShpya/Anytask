package com.company.anytask.api.client;

import com.company.anytask.api.interfaces.*;

public class AnytaskApiClient implements IAnytaskApiClient {
    IOrganizationsApi organizationsApi = new OrganizationsApi();
    ICoursesApi coursesApi = new CoursesApi();
    IUsersApi usersApi = new UsersApi();
    IScoresApi scoresApi;
    ITasksApi tasksApi;

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
        return null;
    }

    @Override
    public ITasksApi tasksApi() {
        return null;
    }

}
