package com.nsdom.globalweather.forecast.network;

import com.nsdom.globalweather.forecast.pojo.Current;
import com.nsdom.globalweather.forecast.pojo.Daily;
import com.nsdom.globalweather.forecast.pojo.Hourly;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @Headers("Content-Type: application/json")
    @GET("/data/2.5/onecall?appid=e2dc9bd71b87c77a0b0d9589c635c791&exclude=current,minutely,daily")
    Call<Hourly> getHourlyData(@Query("lat") Double lat,
                               @Query("lon") Double lon,
                               @Query("units") String units);

    @Headers("Content-Type: application/json")
    @GET("/data/2.5/onecall?appid=e2dc9bd71b87c77a0b0d9589c635c791&exclude=current,minutely,hourly")
    Call<Daily> getDailyData(@Query("lat") Double lat,
                             @Query("lon") Double lon,
                             @Query("units") String units);

    @Headers("Content-Type: application/json")
    @GET("/data/2.5/onecall?appid=e2dc9bd71b87c77a0b0d9589c635c791&exclude=minutely,hourly,daily")
    Call<Current> getCurrentData(@Query("lat") Double lat,
                                 @Query("lon") Double lon,
                                 @Query("units") String units);

}
