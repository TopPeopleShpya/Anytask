package com.company.anytask.api.client;

import com.company.anytask.api.interfaces.IOrganizationsApi;
import com.company.anytask.models.Course;
import com.company.anytask.models.Organization;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;

class OrganizationsApi extends BaseApi implements IOrganizationsApi {
    private static final String TAG = OrganizationsApi.class.getSimpleName();

    OrganizationsApi() {
        super("organizations");
    }

    @Override
    public List<Organization> getOrganizations() {
        Reader reader = getUrl(url);
        return reader == null
                ? null
                : Arrays.asList(gson.fromJson(reader, Organization[].class));
    }

    @Override
    public Organization getOrganization(int id) {
        Reader reader = getUrl(combineUrls(url, id));
        return reader == null
                ? null
                : gson.fromJson(reader, Organization.class);
    }

    @Override
    public List<Course> getCourses(int organizationId) {
        Reader reader = getUrl(combineUrls(url, organizationId, "courses"));
        return reader == null
                ? null
                : Arrays.asList(gson.fromJson(reader, Course[].class));
    }
}
