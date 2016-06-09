package com.goodbox.smsgateway.network;


import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amardeep.
 */
public class NetworkManager {

    private static ApiService apiService;

    private static Retrofit getRetroFit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return  new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static ApiService getApiService() {
        if (apiService == null) {
            apiService = getRetroFit().create(ApiService.class);
        }
        return apiService;
    }

    public static void requestServer(String url, Map<String, String> map, Callback<String> callback) {
        ApiService service = getApiService();
        Call<String> model = service.requestServer(url, map);
        model.enqueue(callback);
    }
}
