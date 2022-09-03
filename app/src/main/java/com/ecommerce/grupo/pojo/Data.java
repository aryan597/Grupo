package com.ecommerce.grupo.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("maximumRetailPrice")
    @Expose
    private float maximumRetailPrice;
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

    public float getMaximumRetailPrice() {
        return maximumRetailPrice;
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
