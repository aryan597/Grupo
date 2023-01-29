package com.ecommerce.grupo.Model;

public class CategoryModel {
    String title,imageUrl;
    int id;
    int parentCategoryId;

    public CategoryModel(String title, String imageUrl, int id) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
