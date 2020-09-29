package com.example.webandcraftsakhil.persistense;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.webandcraftsakhil.requests.category.Categories;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategories(Categories... genre);

    @Query("SELECT * FROM categories ")
    public LiveData<List<Categories>> loadAllCategories();

}
