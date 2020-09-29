package com.example.webandcraftsakhil.requests;


import com.example.webandcraftsakhil.util.Constants;
import com.example.webandcraftsakhil.util.LiveDataCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.webandcraftsakhil.util.Constants.CONNECTION_TIMEOUT;
import static com.example.webandcraftsakhil.util.Constants.READ_TIMEOUT;
import static com.example.webandcraftsakhil.util.Constants.WRITE_TIMEOUT;

public class ServiceGenerator {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(new LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static CategoriesAPI categoriesAPI  = retrofit.create(CategoriesAPI.class);

    public static CategoriesAPI getCategoiresAPI(){ return categoriesAPI; }
}
