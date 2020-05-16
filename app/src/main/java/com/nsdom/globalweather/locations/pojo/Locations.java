package com.nsdom.globalweather.locations.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class Locations {

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("countryCode")
    @Expose
    private String countryCode;

    @SerializedName("lovationId")
    @Expose
    private String locationId;

    @SerializedName("address")
    @Expose
    private LocationsAddress address;

    @SerializedName("matchLevel")
    @Expose
    private String matchLevel;

    public String getLabel() {
        return label;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLocationId() {
        return locationId;
    }

    public LocationsAddress getAddress() {
        return address;
    }

    public String getMatchLevel() {
        return matchLevel;
    }

    @NotNull
    @Override
    public String toString() {
        return "Locations{" +
                "label='" + label + '\'' +
                ", language='" + language + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", locationId='" + locationId + '\'' +
                ", address=" + address +
                ", matchLevel='" + matchLevel + '\'' +
                '}';
    }
}
