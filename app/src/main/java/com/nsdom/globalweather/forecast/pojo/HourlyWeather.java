package com.nsdom.globalweather.forecast.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class HourlyWeather {

    @SerializedName("dt")
    @Expose
    private int hour;

    @SerializedName("temp")
    @Expose
    private Double temperature;

    @SerializedName("feels_like")
    @Expose
    private Double feelsLike;

    @SerializedName("humidity")
    @Expose
    private int humidity;

    @SerializedName("clouds")
    @Expose
    private int clouds;

    @SerializedName("wind_speed")
    @Expose
    private Double windSpeed;

    @SerializedName("wind_deg")
    @Expose
    private int windDegree;

    @SerializedName("weather")
    @Expose
    private ArrayList<Weather> weather;

    public int getHour() {
        return hour;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getClouds() {
        return clouds;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public int getWindDegree() {
        return windDegree;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    @Override
    public String toString() {
        return "HourlyWeather{" +
                "hour=" + hour +
                ", temperature=" + temperature +
                ", feelsLike=" + feelsLike +
                ", humidity=" + humidity +
                ", clouds=" + clouds +
                ", windSpeed=" + windSpeed +
                ", windDegree=" + windDegree +
                ", weather=" + weather +
                '}';
    }
}
