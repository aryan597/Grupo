package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class RemoveCartItems {
    @SerializedName("userId")
    public int userId;
    @SerializedName("productId")
    public int productId;

    public RemoveCartItems(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
