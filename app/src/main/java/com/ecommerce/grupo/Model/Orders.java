package com.ecommerce.grupo.Model;

import com.google.gson.annotations.SerializedName;

public class Orders {
    @SerializedName("id")
    int id;
    @SerializedName("quantity")
    int quantity;
    @SerializedName("productTotalPrice")
    int productTotalPrice;
    @SerializedName("paymentMethod")
    String paymentMethod;
    @SerializedName("productId")
    int productId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(int productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
