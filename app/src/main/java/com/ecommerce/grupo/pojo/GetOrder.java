package com.ecommerce.grupo.pojo;

import com.ecommerce.grupo.Model.Orders;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetOrder {
    @SerializedName("data")
    public ArrayList<Orders> ordersArrayList;
}
