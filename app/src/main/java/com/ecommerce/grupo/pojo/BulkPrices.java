package com.ecommerce.grupo.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BulkPrices implements Serializable {
    @SerializedName("10-49")
    @Expose
    private Integer _1049;
    @SerializedName("50-99")
    @Expose
    private Integer _5099;
    @SerializedName("100-200")
    @Expose
    private Integer _100200;

    public Integer get_1049() {
        return _1049;
    }

    public void set_1049(Integer _1049) {
        this._1049 = _1049;
    }

    public Integer get_5099() {
        return _5099;
    }

    public void set_5099(Integer _5099) {
        this._5099 = _5099;
    }

    public Integer get_100200() {
        return _100200;
    }

    public void set_100200(Integer _100200) {
        this._100200 = _100200;
    }
}
