package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class GetCartItems {
    @SerializedName("Cart")
    public Cart cart;
    @SerializedName("mobileNumber")
    public String mobileNumber;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}

