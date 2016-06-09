package com.goodbox.smsgateway.network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Amardeep.
 */
public interface ApiService {

    @POST
    Call<String> requestServer(@Url String url, @FieldMap Map<String, String> options);
}
