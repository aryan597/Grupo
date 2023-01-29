package com.ecommerce.grupo.pojo;

import com.ecommerce.grupo.Model.CategoryModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetCategory {
    @SerializedName("data")
    public ArrayList<CategoryModel> data = new ArrayList<>();
}
