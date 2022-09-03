package com.ecommerce.grupo.pojo;

import com.ecommerce.grupo.Model.CategoryModel;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Category {
    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;
    @SerializedName("imageUrl")
    public String imageUrl;
    @SerializedName("data")
    public ArrayList<CategoryModel> categoryModels = new ArrayList<>();

    public class Datum {

        @SerializedName("id")
        public String id;
        @SerializedName("title")
        public String title;
        @SerializedName("imageUrl")
        public String imageUrl;

    }
}
