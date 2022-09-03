package com.ecommerce.grupo.pojo;

import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MultipleResource {


    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;
    @SerializedName("imageUrl")
    public String imageUrl;
    @SerializedName("data")
    public ArrayList<ParentCategoryModel> data = new ArrayList<>();

    public class Datum {

        @SerializedName("id")
        public String id;
        @SerializedName("title")
        public String title;
        @SerializedName("imageUrl")
        public String imageUrl;

    }
}
