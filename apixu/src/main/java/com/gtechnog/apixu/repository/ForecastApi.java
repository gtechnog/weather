package com.gtechnog.apixu.repository;

import com.gtechnog.apixu.models.forecast.ForecastWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ForecastApi {

    @GET("forecast.json")
    Call<ForecastWeather> getForecastWeather(@Query("key") String key,
                                             @Query("q") String q,
                                             @Query("days") int days);

}
