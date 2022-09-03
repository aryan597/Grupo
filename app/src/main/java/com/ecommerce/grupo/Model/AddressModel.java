package com.ecommerce.grupo.Model;

import com.ecommerce.grupo.pojo.UserAddress;

import java.util.ArrayList;

public class AddressModel {
    public ArrayList<AddressModel> data;
    private String name,address1,city,state,postalCode;

    public AddressModel(String name, String address1, String city, String state, String postalCode) {
        this.name = name;
        this.address1 = address1;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public AddressModel(UserAddress addressModel) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
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
}
