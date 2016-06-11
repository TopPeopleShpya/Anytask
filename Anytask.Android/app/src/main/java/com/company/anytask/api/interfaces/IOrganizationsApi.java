package com.company.anytask.api.interfaces;

import com.company.anytask.api.HttpStatusCode;
import com.company.anytask.models.Organization;

import java.util.Collection;

public interface IOrganizationsApi {
    Collection<Organization> getOrganizations();
    Organization getOrganization(int id);
}
