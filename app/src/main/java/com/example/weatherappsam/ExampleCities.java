package com.example.weatherappsam;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ExampleCities {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private ArrayList<Datum> data;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

}
