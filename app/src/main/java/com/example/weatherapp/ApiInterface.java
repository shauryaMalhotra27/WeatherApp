package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("forecast.json")
    Call<Example> getweather(@Query("q") String cityName, @Query("key") String apiKey);

}
