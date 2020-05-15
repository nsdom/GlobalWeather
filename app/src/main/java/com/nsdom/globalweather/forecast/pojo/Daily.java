package com.nsdom.globalweather.forecast.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Daily {

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("daily")
    @Expose
    private ArrayList<DailyWeather> daily;

    public String getTimezone() {
        return timezone;
    }

    public ArrayList<DailyWeather> getDaily() {
        return daily;
    }

    @Override
    public String toString() {
        return "Daily{" +
                "timezone='" + timezone + '\'' +
                ", daily=" + daily +
                '}';
    }
}
