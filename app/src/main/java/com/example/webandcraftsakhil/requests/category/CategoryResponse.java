package com.example.webandcraftsakhil.requests.category;

import java.util.List;

public class CategoryResponse {
    boolean status;
    String msg;
    List<Categories> categories;

    public CategoryResponse(boolean status, String msg, List<Categories> categories) {
        this.status = status;
        this.msg = msg;
        this.categories = categories;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }
}
