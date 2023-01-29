package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class AddtoBag {
    @SerializedName("userId")
    public int userId;
    @SerializedName("productId")
    public int productId;

    public AddtoBag(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
