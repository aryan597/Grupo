package com.ecommerce.grupo.Model;

import com.ecommerce.grupo.pojo.BulkPrices;
import com.ecommerce.grupo.pojo.Img;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartItems implements Serializable {
    public int id;
    public String title;
    public String description;
    public Img img;
    @SerializedName("price")
    public int maximumRetailPrice;
    public int userQuantity;
    public BulkPrices bulkPrices;

    public BulkPrices getBulkPrices() {
        return bulkPrices;
    }

    public void setBulkPrices(BulkPrices bulkPrices) {
        this.bulkPrices = bulkPrices;
    }

    public int getUserQuantity() {
        return userQuantity;
    }

    public void setUserQuantity(int userQuantity) {
        this.userQuantity = userQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaximumRetailPrice() {
        return maximumRetailPrice;
    }

    public void setMaximumRetailPrice(int maximumRetailPrice) {
        this.maximumRetailPrice = maximumRetailPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }
}
