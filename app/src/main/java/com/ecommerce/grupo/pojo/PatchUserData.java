package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class PatchUserData {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("mobileNumber")
    public String mobileNumber;
    @SerializedName("email")
    public String email;
    @SerializedName("gender")
    public String gender;

    public PatchUserData(String name, String email, String gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
    }
}
