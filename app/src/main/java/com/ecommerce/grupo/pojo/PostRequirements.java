package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.SerializedName;

public class PostRequirements {
    @SerializedName("parentCategoryId")
    public int parentCategoryId;
    @SerializedName("categoryId")
    public int categoryId;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("quantity")
    public int quantity;
    @SerializedName("image")
    public Img img;
    @SerializedName("userId")
    public int userId;

    public PostRequirements(int parentCategoryId, int categoryId, String title, String description, int quantity, Img img, int userId) {
        this.parentCategoryId = parentCategoryId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.img = img;
        this.userId = userId;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
