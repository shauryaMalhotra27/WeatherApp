package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface2 {

    @GET("countries")
    Call<ExampleCities> citiesCountry();
}
