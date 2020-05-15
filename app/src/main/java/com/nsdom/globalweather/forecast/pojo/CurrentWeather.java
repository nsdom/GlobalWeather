package com.nsdom.globalweather.forecast.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentWeather {

    @SerializedName("dt")
    @Expose
    private long time;

    @SerializedName("sunrise")
    @Expose
    private long sunrise;

    @SerializedName("sunset")
    @Expose
    private long sunset;

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

    public long getTime() {
        return time;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
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
        return "CurrentWeather{" +
                "time=" + time +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
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
