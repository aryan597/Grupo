package com.ecommerce.grupo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserModel {
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
    @SerializedName("gender")
    public String gender;
    @SerializedName("merchantId")
    public int merchantId;
    public ArrayList<UserModel> data;

    public UserModel(String id, String name, String mobileNumber, String email, String password, Boolean isPhoneVerified, Boolean isEmailVerified, String role, String address1, String address2, String city, String state, String postalCode, int merchantId, ArrayList<UserModel> data) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        this.isPhoneVerified = isPhoneVerified;
        this.isEmailVerified = isEmailVerified;
        this.role = role;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.merchantId = merchantId;
        this.data = data;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPhoneVerified() {
        return isPhoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        isPhoneVerified = phoneVerified;
    }

    public Boolean getEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public ArrayList<UserModel> getData() {
        return data;
    }

    public void setData(ArrayList<UserModel> data) {
        this.data = data;
    }
}

