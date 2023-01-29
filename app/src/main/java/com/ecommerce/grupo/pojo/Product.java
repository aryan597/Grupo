package com.ecommerce.grupo.pojo;

import com.ecommerce.grupo.Model.CategoryModel;
import com.ecommerce.grupo.Model.ProductModel;
import com.ecommerce.grupo.Model.images;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product {
    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("maximumRetailPrice")
    public String maximumRetailPrice;
    @SerializedName("ratings")
    public int ratings;

    @SerializedName("img")
    @Expose
    private Img img;
    @SerializedName("data")
    public ArrayList<ProductModel> productModels = new ArrayList<>();
    public Img getImg() {
        return img;
    }

}
