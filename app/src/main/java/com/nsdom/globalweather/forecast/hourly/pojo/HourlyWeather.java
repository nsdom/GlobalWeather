package com.nsdom.globalweather.forecast.hourly.pojo;

public class HourlyWeather {

    private final String hour, temperature, wind;

    public HourlyWeather(String hour, String temperature, String wind) {
        this.hour = hour;
        this.temperature = temperature;
        this.wind = wind;
    }

    public String getHour() {
        return hour;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWind() {
        return wind;
    }

    @Override
    public String toString() {
        return "HourlyWeather{" +
                "hour='" + hour + '\'' +
                ", temperature='" + temperature + '\'' +
                ", wind='" + wind + '\'' +
                '}';
    }
}
