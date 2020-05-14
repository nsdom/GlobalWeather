package com.nsdom.globalweather.forecast.hourly.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("main")
    @Expose
    private String main;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("icon")
    @Expose
    private String icon;

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
