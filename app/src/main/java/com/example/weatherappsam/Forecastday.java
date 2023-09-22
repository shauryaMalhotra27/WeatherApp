
package com.example.weatherappsam;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Forecastday {

    @SerializedName("date")

    private String date;
    @SerializedName("date_epoch")

    private Integer dateEpoch;
    @SerializedName("day")

    private Day day;
    @SerializedName("astro")

    private Astro astro;
    @SerializedName("hour")

    private List<Hour> hour;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDateEpoch() {
        return dateEpoch;
    }

    public void setDateEpoch(Integer dateEpoch) {
        this.dateEpoch = dateEpoch;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Astro getAstro() {
        return astro;
    }

    public void setAstro(Astro astro) {
        this.astro = astro;
    }

    public List<Hour> getHour() {
        return hour;
    }

    public void setHour(List<Hour> hour) {
        this.hour = hour;
    }

}
