package com.example.webandcraftsakhil.requests;


import androidx.lifecycle.LiveData;

import com.example.webandcraftsakhil.requests.category.CategoryResponse;

import retrofit2.http.GET;

public abstract interface CategoriesAPI {

    // Get Genre
    @GET("/v2/5ec39cba300000720039c1f6")
    LiveData<ApiResponse<CategoryResponse>> getCategoryData();
}
