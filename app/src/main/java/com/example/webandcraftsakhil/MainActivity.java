package com.example.webandcraftsakhil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webandcraftsakhil.adapter.ItemAdapter;
import com.example.webandcraftsakhil.adapter.OnMainProductClickListener;
import com.example.webandcraftsakhil.requests.category.Categories;
import com.example.webandcraftsakhil.requests.category.Products;
import com.example.webandcraftsakhil.util.Resource;
import com.example.webandcraftsakhil.viewmodels.CategoryViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMainProductClickListener {
    private static final String TAG = "MainActivity";
    private CategoryViewModel mViewModel;
    RecyclerView rvCategories;
    Context mContext;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Categories");
        rvCategories = findViewById(R.id.rv_categories);
        mContext = this;
        mViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        mViewModel.getCategories();
        initRecycleView();
        mViewModel.loadCategories().observe(this, new Observer<Resource<List<Categories>>>() {
            @Override
            public void onChanged(Resource<List<Categories>> listResource) {
                Log.d(TAG, "onChanged: ");
                if (listResource != null) {
                    if (listResource.data != null) {
                        switch (listResource.status) {
                            case LOADING: {
                                break;
                            }
                            case ERROR: {
                                Log.e(TAG, "onChanged: Cannot refresh the cache");
                                Log.e(TAG, "onChanged: " + listResource.message);
                                itemAdapter.setCategories(listResource.data);
                                break;
                            }
                            case SUCCESS: {
                                Log.d(TAG, "onChanged:Success ");
                                List<Categories> categoriesList = listResource.data;
                                List<Products> products = categoriesList.get(0).getProducts();
                                Log.d(TAG, "onChanged: " + products.get(0).getDescription());
                                itemAdapter.setCategories(listResource.data);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    public void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        itemAdapter = new ItemAdapter(mContext);
        itemAdapter.setOnMainProductClickListener(this);
        rvCategories.setAdapter(itemAdapter);
        rvCategories.setLayoutManager(layoutManager);
    }

    @Override
    public void onMainProductClick(Products products) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("product", products);
        startActivity(intent);
    }
}