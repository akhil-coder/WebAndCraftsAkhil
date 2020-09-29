package com.example.webandcraftsakhil.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.webandcraftsakhil.persistense.CategoryDatabase;
import com.example.webandcraftsakhil.repository.CategoryRepository;
import com.example.webandcraftsakhil.requests.CategoriesAPI;
import com.example.webandcraftsakhil.requests.category.Categories;
import com.example.webandcraftsakhil.util.Resource;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private static final String TAG = "CategoryViewModel";
    private MediatorLiveData<Resource<List<Categories>>> mCategories = new MediatorLiveData<>();
    private CategoryRepository mRepository;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = CategoryRepository.getInstance(application);
    }

    public LiveData<Resource<List<Categories>>> loadCategories(){
        return mCategories;
    }

    public void getCategories(){
        final LiveData<Resource<List<Categories>>> repositorySource = mRepository.getMovieDetails();
        mCategories.addSource(repositorySource, new Observer<Resource<List<Categories>>>() {
            @Override
            public void onChanged(Resource<List<Categories>> listResource) {
                Log.d(TAG, "onChanged: Genre Details fetched ");
                mCategories.setValue(listResource);
            }
        });    }
}
