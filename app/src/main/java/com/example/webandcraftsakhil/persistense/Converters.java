package com.example.webandcraftsakhil.persistense;

import androidx.room.TypeConverter;

import com.example.webandcraftsakhil.requests.category.Products;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public String fromCountryLangList(List<Products> products) {
        if (products == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Products>>() {}.getType();
        String json = gson.toJson(products, type);
        return json;
    }

    @TypeConverter
    public List<Products> toCountryLangList(String productString) {
        if (productString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Products>>() {}.getType();
        List<Products> countryLangList = gson.fromJson(productString, type);
        return countryLangList;
    }
}
