package com.ecommerce.grupo.pojo;

import com.ecommerce.grupo.Model.EnquiryModel;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Enquiry {
    @SerializedName("userName")
    public String userName;
    @SerializedName("phoneNumber")
    public String phoneNumber;
    @SerializedName("productName")
    public String productName;
    @SerializedName("productId")
    public int productId;
    @SerializedName("totalPrice")
    public String totalPrice;
    @SerializedName("merchantName")
    public String merchantName;
    @SerializedName("merchantPhone")
    public String merchantPhone;
    @SerializedName("merchantAddress")
    public String merchantAddress;
    @SerializedName("quantity")
    public int quantity;
    @SerializedName("userId")
    public int userId;
    @SerializedName("data")
    public String data;

    public Enquiry(String userName, String phoneNumber, String productName, int productId, String totalPrice, String merchantName, String merchantPhone, String merchantAddress, int quantity, int userId) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.productName = productName;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.merchantName = merchantName;
        this.merchantPhone = merchantPhone;
        this.merchantAddress = merchantAddress;
        this.quantity = quantity;
        this.userId = userId;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
