package com.example.android.bakingapp.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    static BakingUtils bakingUtils;

    public static BakingUtils Retrieve(){
        Gson gson = new GsonBuilder().create();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        bakingUtils = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(builder.build())
                .build().create(BakingUtils.class);
return bakingUtils;
    }
}
