package com.example.webandcraftsakhil.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.webandcraftsakhil.persistense.CategoryDao;
import com.example.webandcraftsakhil.persistense.CategoryDatabase;
import com.example.webandcraftsakhil.requests.ApiResponse;
import com.example.webandcraftsakhil.requests.ServiceGenerator;
import com.example.webandcraftsakhil.requests.category.Categories;
import com.example.webandcraftsakhil.requests.category.CategoryResponse;
import com.example.webandcraftsakhil.util.AppExecutors;
import com.example.webandcraftsakhil.util.NetworkBoundResource;
import com.example.webandcraftsakhil.util.Resource;

import java.util.List;

public class CategoryRepository {
    private static final String TAG = "CategoryRepository";
    private static CategoryRepository instance;
    private CategoryDao categoryDao;

    public static CategoryRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CategoryRepository(context);
        }
        return instance;
    }

    public CategoryRepository(Context context) {
        this.categoryDao = CategoryDatabase.getInstance(context).getCategoryDao();
    }

    public LiveData<Resource<List<Categories>>> getMovieDetails() {
        return new NetworkBoundResource<List<Categories>, CategoryResponse>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull CategoryResponse item) {
                if(item.getCategories() != null){
                    Log.d(TAG, "saveCallResult: ");
                    Categories[] categoriesList = new Categories[item.getCategories().size()];
                    categoryDao.insertCategories(item.getCategories().toArray(categoriesList));
                    if(categoriesList != null){
                        Log.d(TAG, "saveCallResult: Categories" + categoriesList[0].getTitle());
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Categories> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Categories>> loadFromDb() {
                return categoryDao.loadAllCategories();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CategoryResponse>> createCall() {
                return ServiceGenerator.getCategoiresAPI().getCategoryData();
            }
        }.getAsLiveData();
    }

}
