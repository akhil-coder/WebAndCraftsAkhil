package com.example.webandcraftsakhil.requests.category;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "categories")
public class Categories {

    @PrimaryKey
    @NonNull
    public String title;
    public List<Products> products;
    @Ignore
    public Boolean isExpanded;
    public Categories(List<Products> products, String title) {
        this.products = products;
        this.title = title;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }
}
