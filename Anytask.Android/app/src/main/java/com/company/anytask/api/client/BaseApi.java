package com.company.anytask.api.client;

import android.util.Log;
import com.company.anytask.Config;
import com.company.anytask.api.HttpStatusCode;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

class BaseApi {
    private static final String TAG = BaseApi.class.getSimpleName();
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    Gson gson = new Gson();
    String url;

    BaseApi(String suffix) {
        url = combineUrls(Config.API_URL, suffix);
    }

    Reader getUrl(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            HttpStatusCode httpStatusCode = HttpStatusCode.fromCode(response.code());
            switch (httpStatusCode) {
                case OK:
                    return response.body().charStream();
                default:
                    Log.e(TAG, "There are some issues at server side\n" +
                            httpStatusCode.asText() + ":" + httpStatusCode.getDesc());
                    return null;
            }

        } catch (SocketTimeoutException e) {
            Log.e(TAG, "Time limit exceeded while processing request:\n" + url, e);
        } catch (IOException e) {
            Log.e(TAG, "Failed to retrieve url: " + url, e);
        } catch (Exception e) {
            Log.e(TAG, "Unexpected exception was thrown", e);
            throw e;
        }
        return null;
    }

    static String combineUrls(Object... urls) {
        StringBuilder resultUrl = new StringBuilder();
        for (Object url : urls) {
            resultUrl
                    .append(url)
                    .append("/");
        }
        return resultUrl.toString();
    }
}
