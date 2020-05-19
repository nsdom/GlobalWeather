package com.nsdom.globalweather.network;

import com.nsdom.globalweather.locations.pojo.HereGeocoderResponse;
import com.nsdom.globalweather.locations.pojo.HereSearchResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface HereApi {

    @Headers("Content-Type: application/json")
    @GET("/6.2/suggest.json?&app_id=JpRTMYwvlfjhJOXdtlwb&app_code=4j6ON24xwLzuK62r7nZ3oA")
    Observable<HereSearchResponse> getSuggestLocations(@Query("query") String searchLocation);

    @Headers("Content-Type: application/json")
    @GET("/6.2/geocode.json?app_id=JpRTMYwvlfjhJOXdtlwb&app_code=4j6ON24xwLzuK62r7nZ3oA&jsonattributes=1&gen=9")
    Call<HereGeocoderResponse> getCoordinates(@Query("locationId") String locationId);

}
