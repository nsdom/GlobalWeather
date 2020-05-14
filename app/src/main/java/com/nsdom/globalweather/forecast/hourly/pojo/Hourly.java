package com.nsdom.globalweather.forecast.hourly.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Hourly {

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("hourly")
    @Expose
    private ArrayList<HourlyWeather> hourly;

    public String getTimezone() {
        return timezone;
    }

    public ArrayList<HourlyWeather> getHourly() {
        return hourly;
    }

    @Override
    public String toString() {
        return "Hourly{" +
                "timezone='" + timezone + '\'' +
                ", hourly=" + hourly +
                '}';
    }
}
