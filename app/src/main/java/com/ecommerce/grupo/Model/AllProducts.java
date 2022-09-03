package com.ecommerce.grupo.Model;

import com.ecommerce.grupo.pojo.Img;

import java.io.Serializable;

public class AllProducts {
    private Img img;
    String  title,description;
    int id;

    public AllProducts(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
