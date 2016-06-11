package com.company.anytask.api.interfaces;

public interface IAnytaskApiClient {
    ICoursesApi coursesApi();
    IOrganizationsApi organizationsApi();
    IScoresApi scoresApi();
    ITasksApi tasksApi();
}
