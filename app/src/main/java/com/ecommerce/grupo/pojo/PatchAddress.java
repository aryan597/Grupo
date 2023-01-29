package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class PatchAddress {
    @SerializedName("Address")
    public String address;
    @SerializedName("city")
    public String city;
    @SerializedName("state")
    public String state;
    @SerializedName("postalCode")
    public String postalCode;

    public PatchAddress(String address, String city, String state, String postalCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }
}
