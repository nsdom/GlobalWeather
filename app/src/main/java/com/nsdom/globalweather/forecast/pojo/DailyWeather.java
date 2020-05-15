package com.nsdom.globalweather.forecast.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DailyWeather {

    @SerializedName("dt")
    @Expose
    private Long date;

    @SerializedName("sunrise")
    @Expose
    private Long sunrise;

    @SerializedName("sunset")
    @Expose
    private Long sunset;

    @SerializedName("temp")
    @Expose
    private Temperature temperatures;


    @SerializedName("humidity")
    @Expose
    private int humidity;

    @SerializedName("wind_speed")
    @Expose
    private Double windSpeed;

    @SerializedName("wind_deg")
    @Expose
    private int windDegree;

    @SerializedName("clouds")
    @Expose
    private int clouds;

    @SerializedName("weather")
    @Expose
    private ArrayList<Weather> weather;

    public Long getDate() {
        return date;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public Temperature getTemperatures() {
        return temperatures;
    }

    public int getHumidity() {
        return humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public int getWindDegree() {
        return windDegree;
    }

    public int getClouds() {
        return clouds;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    @Override
    public String toString() {
        return "DailyWeather{" +
                "date=" + date +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", temperatures=" + temperatures +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", windDegree=" + windDegree +
                ", clouds=" + clouds +
                ", weather=" + weather +
                '}';
    }
}
