package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class StoreUrl {
    @SerializedName("Url")
    public String Url;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
