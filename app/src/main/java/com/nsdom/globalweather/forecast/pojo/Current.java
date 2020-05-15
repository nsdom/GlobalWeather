package com.nsdom.globalweather.forecast.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Current {

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("current")
    @Expose
    private CurrentWeather current;

    public String getTimezone() {
        return timezone;
    }

    public CurrentWeather getCurrent() {
        return current;
    }

    @Override
    public String toString() {
        return "Current{" +
                "timezone='" + timezone + '\'' +
                ", current=" + current +
                '}';
    }
}
