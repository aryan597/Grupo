package com.ecommerce.grupo.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data  {
    private float id;
    @SerializedName("title")
    String title;
    private String description;
    @SerializedName("img")
    @Expose
    private Img img;
    @SerializedName("bulkPrices")
    @Expose
    private BulkPrices bulkPrices;
    private float wholeSalePrice;
    private float orderSamplePrice;
    private float ratings;
    private float groupPrice;
    private String offerStartTime;
    private String offerEndTime;
    private boolean isOfferActive;
    private float groupThresholdUsers;
    private boolean isRenewable;
    private boolean isCODEligible;
    @SerializedName("merchantId")
    @Expose
    private int merchantId;
    private float categoryId;
    private float quantity;
    private ArrayList<String> sizes;
    private String color;
    private int gsm,wsp;
    private String sku;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<String> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<String> sizes) {
        this.sizes = sizes;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getGsm() {
        return gsm;
    }

    public void setGsm(int gsm) {
        this.gsm = gsm;
    }

    public int getWsp() {
        return wsp;
    }

    public void setWsp(int wsp) {
        this.wsp = wsp;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Img getImg() {
        return img;
    }

    public BulkPrices getBulkPrices() {
        return bulkPrices;
    }

    public void setBulkPrices(BulkPrices bulkPrices) {
        this.bulkPrices = bulkPrices;
    }

    public String getTitle() {
        return title;
    }

    public float getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }


    public float getWholeSalePrice() {
        return wholeSalePrice;
    }

    public float getOrderSamplePrice() {
        return orderSamplePrice;
    }

    public float getRatings() {
        return ratings;
    }

    public float getGroupPrice() {
        return groupPrice;
    }

    public String getOfferStartTime() {
        return offerStartTime;
    }

    public String getOfferEndTime() {
        return offerEndTime;
    }

    public boolean isOfferActive() {
        return isOfferActive;
    }

    public float getGroupThresholdUsers() {
        return groupThresholdUsers;
    }

    public boolean isRenewable() {
        return isRenewable;
    }

    public boolean isCODEligible() {
        return isCODEligible;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public float getCategoryId() {
        return categoryId;
    }

    public float getQuantity() {
        return quantity;
    }
}
