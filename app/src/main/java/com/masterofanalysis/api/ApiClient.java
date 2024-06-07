package com.masterofanalysis.api;

import com.masterofanalysis.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(Constants.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
            httpClient.readTimeout(Constants.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
            httpClient.writeTimeout(Constants.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
