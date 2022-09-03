package com.ecommerce.grupo.pojo;

import com.ecommerce.grupo.Model.AddressModel;
import com.ecommerce.grupo.Model.AllProducts;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllProductsPojo {
    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("img")
    @Expose
    private Img img;
    @SerializedName("data")
    public ArrayList<AllProducts> data = new ArrayList<>();

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }
}
