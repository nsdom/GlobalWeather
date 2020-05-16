package com.nsdom.globalweather.locations.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationsAddress {

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("county")
    @Expose
    private String county;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("district")
    @Expose
    private String district;

    @SerializedName("street")
    @Expose
    private String street;

    @SerializedName("postalCode")
    @Expose
    private String postalCode;

    public String getCountry() {
        return country;
    }

    public String getCounty() {
        return county;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "LocationsAddress{" +
                "country='" + country + '\'' +
                ", county='" + county + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}


