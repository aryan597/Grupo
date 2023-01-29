package com.ecommerce.grupo.Model;

import com.ecommerce.grupo.pojo.Img;

import java.util.ArrayList;

public class ProductModel {
    private Img img;
    String title,imageUrl,description;
    int id,ratings,price;

    public ProductModel(String title, String imageUrl, String description, int id, int ratings) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.id = id;
        this.ratings = ratings;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
