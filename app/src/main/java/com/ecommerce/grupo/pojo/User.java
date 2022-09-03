package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("phoneNumber")
    public String phone;
    @SerializedName("data")
    public String data;
    @SerializedName("userId")
    public String userId;

    public User(String phone) {
        this.phone = phone;
    }


}
