package com.ecommerce.grupo.pojo;

import com.ecommerce.grupo.Model.AddressModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PatchAddress {
    @SerializedName("address1")
    public String address;
    @SerializedName("city")
    public String city;
    @SerializedName("state")
    public String state;
    @SerializedName("postalCode")
    public String postalCode;
    @SerializedName("data")
    public ArrayList<AddressModel> addressModelArrayList;

    public PatchAddress(String address, String city, String state, String postalCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }
}
