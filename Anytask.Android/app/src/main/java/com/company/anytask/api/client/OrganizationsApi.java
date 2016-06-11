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
import java.io.IOException;
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
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            return Arrays.asList(gson.fromJson(response.body().charStream(), Organization[].class));
        } catch (IOException e) {
            Log.e(TAG, "getOrganizations: ", e);
        }
        return null;
    }

    @Override
    public Organization getOrganization(int id) {
        return null;
    }
}
