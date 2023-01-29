package com.ecommerce.grupo.pojo;

import com.ecommerce.grupo.Model.CartItems;

import java.util.ArrayList;

public class Cart {
    public int id;
    public int userId;
    public ArrayList<CartItems> Product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<CartItems> getProduct() {
        return Product;
    }

    public void setProduct(ArrayList<CartItems> product) {
        Product = product;
    }
}
