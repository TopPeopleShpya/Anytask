package com.company.anytask.api.client;

import android.util.Log;
import com.company.anytask.Config;
import com.company.anytask.R;
import com.company.anytask.api.interfaces.IOrganizationsApi;
import com.company.anytask.models.Course;
import com.company.anytask.models.Organization;
import com.google.gson.Gson;
import com.google.gson.internal.Excluder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.crypto.spec.OAEPParameterSpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class OrganizationsApi implements IOrganizationsApi {
    private static final String TAG = OrganizationsApi.class.getSimpleName();
    private static final String SUFFIX = "organizations/";

    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();
    private String url;

    public OrganizationsApi() {
        url = Config.API_URL + SUFFIX;
    }

    private String getJsonString(String url) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null)
                buffer.append(line).append("\n");

            return buffer.toString();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (connection != null)
                connection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public Collection<Organization> getOrganizations() {
        return Arrays.asList(gson.fromJson(getJsonString(url), Organization[].class));
    }

    @Override
    public Organization getOrganization(int id) {
        try {
            Organization org = gson.fromJson(getJsonString(url + id), Organization.class);
            Course[] courses = gson.fromJson(getJsonString(url + id + "/courses"), Course[].class);
            org.courses = Arrays.asList(courses);

            return org;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }
}
