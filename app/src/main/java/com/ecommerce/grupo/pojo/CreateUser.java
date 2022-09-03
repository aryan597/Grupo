package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class CreateUser {

    @SerializedName("name")
    public String name;
    @SerializedName("mobileNumber")
    public String mobileNumber;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("isPhoneVerified")
    public Boolean isPhoneVerified;
    @SerializedName("isEmailVerified")
    public  Boolean isEmailVerified;
    @SerializedName("role")
    public String role;

    public CreateUser(String name, String mobileNumber, String email, String password, Boolean isPhoneVerified, Boolean isEmailVerified, String role) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        this.isPhoneVerified = isPhoneVerified;
        this.isEmailVerified = isEmailVerified;
        this.role = role;
    }
}
