package com.company.anytask.api.client;

import android.util.Log;
import com.company.anytask.Config;
import com.company.anytask.R;
import com.company.anytask.api.interfaces.IOrganizationsApi;
import com.company.anytask.models.Organization;
import com.google.gson.Gson;
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

    @Override
    public Collection<Organization> getOrganizations() {
        //
        //Request request = new Request.Builder()
        //        .url(url)
        //        .build();
        //try {
        //    Response response = client.newCall(request).execute();
        //    return Arrays.asList(gson.fromJson(response.body().charStream(), Organization[].class));
        //} catch (IOException e) {
        //    Log.e(TAG, "getOrganizations: ", e);
        //}
        //return null;
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
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

            return Arrays.asList(gson.fromJson(buffer.toString(), Organization[].class));
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
    public Organization getOrganization(int id) {
        return null;
    }
}
