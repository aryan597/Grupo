package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class CreateOrder {
    @SerializedName("id")
    String id;
    @SerializedName("addressId")
    public int address;
    @SerializedName("userId")
    int userId;
    @SerializedName("productId")
    int productId;
    @SerializedName("quantity")
    int quantity;
    @SerializedName("productTotalPrice")
    Integer productTotalPrice;
    @SerializedName("paymentMethod")
    String paymentMethod;
    @SerializedName("type")
    String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CreateOrder(int userId, int productId, int quantity, Integer productTotalPrice, String paymentMethod,String type,int address) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.productTotalPrice = productTotalPrice;
        this.paymentMethod = paymentMethod;
        this.type = type;
        this.address = address;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(Integer productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
