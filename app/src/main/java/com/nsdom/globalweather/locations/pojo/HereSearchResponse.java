package com.nsdom.globalweather.locations.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HereSearchResponse {

    @SerializedName("suggestions")
    @Expose
    private List<Locations> locations;

    public List<Locations> getLocations() {
        return locations;
    }
}
