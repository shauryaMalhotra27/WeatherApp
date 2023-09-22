package com.example.weatherappsam;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Datum {

    @SerializedName("iso2")
    private String iso2;
    @SerializedName("iso3")
    private String iso3;
    @SerializedName("country")
    private String country;
    @SerializedName("cities")
    private ArrayList<String> cities;

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

}
