package com.ecommerce.grupo.Model;

public class CategoryModel {
    String title,imageUrl;
    int id;

    public CategoryModel(String title, String imageUrl, int id) {
        this.title = title;
        this.imageUrl = imageUrl;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
