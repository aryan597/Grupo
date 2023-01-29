package com.ecommerce.grupo.Model;

import java.util.ArrayList;

public class ParentCategoryModel {
    private String title,imageUrl;
    private int id;
    private int parentCategoryId;
    private ArrayList<CategoryModel> Category;

    public ParentCategoryModel(String title, String imageUrl, int id, ArrayList<CategoryModel> Category) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.id = id;
        this.Category = Category;
    }


    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public ArrayList<CategoryModel> getCategoryModels() {
        return Category;
    }

    public void setCategoryModels(ArrayList<CategoryModel> categoryModels) {
        this.Category = categoryModels;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
