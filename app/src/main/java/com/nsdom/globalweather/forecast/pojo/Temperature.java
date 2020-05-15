package com.nsdom.globalweather.forecast.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temperature {

    @SerializedName("max")
    @Expose
    private Double max;

    @SerializedName("min")
    @Expose
    private Double min;


    public Double getMax() {
        return max;
    }

    public Double getMin() {
        return min;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "max=" + max +
                ", min=" + min +
                '}';
    }
}
