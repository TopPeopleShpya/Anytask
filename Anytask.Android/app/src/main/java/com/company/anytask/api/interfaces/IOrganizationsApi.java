package com.company.anytask.api.interfaces;

import com.company.anytask.api.HttpStatusCode;
import com.company.anytask.models.Course;
import com.company.anytask.models.Organization;

import java.util.Collection;
import java.util.List;

public interface IOrganizationsApi {
    List<Organization> getOrganizations();
    Organization getOrganization(int id);
    List<Course> getCourses(int organizationId);
}
