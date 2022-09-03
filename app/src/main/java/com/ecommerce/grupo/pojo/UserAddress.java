package com.ecommerce.grupo.pojo;

import android.provider.ContactsContract;

import com.ecommerce.grupo.Model.AddressModel;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.ecommerce.grupo.Model.UserModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserAddress {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
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
    public ArrayList<AddressModel> data = new ArrayList<>();
}
