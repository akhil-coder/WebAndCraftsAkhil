package com.example.webandcraftsakhil.persistense;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.example.webandcraftsakhil.requests.category.Categories;

@Database(entities = {Categories.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class CategoryDatabase extends RoomDatabase {
   public static final String DATABASE_NAME = "category_database";
   public static CategoryDatabase instance;

   public static CategoryDatabase getInstance(final Context context) {
       if (instance == null) {
           instance = Room.databaseBuilder(context.getApplicationContext(),
                   CategoryDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
       }
       return instance;
   }

   public abstract CategoryDao getCategoryDao();
}
