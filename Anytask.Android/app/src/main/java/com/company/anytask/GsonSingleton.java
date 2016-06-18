package com.company.anytask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSingleton {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            @SuppressWarnings("UnnecessaryLocalVariable")
            Gson localGson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
            gson = localGson;
        }
        return gson;
    }
}
