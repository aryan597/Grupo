package com.ecommerce.grupo.pojo;

import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ParentCategory {
    @SerializedName("data")
    public ArrayList<ParentCategoryModel> data = new ArrayList<>();

}

