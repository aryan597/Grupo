package com.ecommerce.grupo.Model;

import com.google.gson.annotations.SerializedName;

public class EnquiryModel {
    public String userName;
    public String phoneNumber;
    public String productName;
    public int productId;
    public int id;
    public String totalPrice;
    public String merchantName;
    public String merchantPhone;
    public String merchantAddress;
    public int quantity;
    public int userId;

    public EnquiryModel(String userName, String phoneNumber, String productName, int productId, String totalPrice, String merchantName, String merchantPhone, String merchantAddress, int quantity, int userId) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
