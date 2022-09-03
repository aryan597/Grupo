package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class MerchantData {
    @SerializedName("userId")
    private int userId;
    @SerializedName("companyName")
    private String companyName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
