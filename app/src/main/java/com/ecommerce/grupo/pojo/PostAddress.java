package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class PostAddress {
    @SerializedName("Address")
    public String address;
    @SerializedName("city")
    public String city;
    @SerializedName("state")
    public String state;
    @SerializedName("postalCode")
    public String postalCode;
    @SerializedName("userId")
    public int userId;
    @SerializedName("type")
    public String type;

    public PostAddress(String address, String city, String state, String postalCode, int userId,String type) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.userId = userId;
        this.type = type;
    }
}
