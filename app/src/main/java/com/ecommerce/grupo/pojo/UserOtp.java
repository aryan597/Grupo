package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class UserOtp {

    @SerializedName("id")
    public Integer id;
    @SerializedName("code")
    public String code;
    @SerializedName("data")
    public String data;

    public UserOtp(Integer id, String code) {
        this.id = id;
        this.code = code;
    }
}
