package com.example.webandcraftsakhil.requests.category;

import java.io.Serializable;

public class Products implements Serializable {

    public String description;
    public String imageUrl;
    public int price;
    public String title;

    public Products(String description, String imageurl, int price, String title) {
        this.description = description;
        this.imageUrl = imageurl;
        this.price = price;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageUrl;
    }

    public void setImageurl(String imageurl) {
        this.imageUrl = imageurl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
