package com.gtechnog.apixu.repository;

import com.gtechnog.apixu.models.current.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface CurrentWeatherApi {

    @GET("current.json")
    Call<CurrentWeather> currentWeather(@Query("key") String key,
                                        @Query("q") String q);
}
