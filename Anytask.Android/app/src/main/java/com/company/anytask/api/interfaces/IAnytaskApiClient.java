package com.company.anytask.api.interfaces;

public interface IAnytaskApiClient {
    ICoursesApi coursesApi();
    IOrganizationsApi organizationsApi();
    IUsersApi usersApi();
    IScoresApi scoresApi();
    ITasksApi tasksApi();
}
