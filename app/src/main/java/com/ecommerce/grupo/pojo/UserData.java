package com.ecommerce.grupo.pojo;

import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.ecommerce.grupo.Model.UserModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserData {
    @SerializedName("id")
    public String id;
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
    public Boolean isEmailVerified;
    @SerializedName("role")
    public String role;
    @SerializedName("address1")
    public String address1;
    @SerializedName("address2")
    public String address2;
    @SerializedName("city")
    public String city;
    @SerializedName("state")
    public String state;
    @SerializedName("postalCode")
    public String postalCode;
    @SerializedName("merchantId")
    public int merchantId;
    @SerializedName("data")
    public ArrayList<UserModel> data = new ArrayList<>();

}
