package com.gtechnog.apixu.repository;

import com.gtechnog.apixu.models.current.CurrentWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentWeatherRepository {

    private static CurrentWeatherRepository sInstance;
    private final Retrofit retrofit;
    private final CurrentWeatherApi currentWeatherApi;

    private CurrentWeatherRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        currentWeatherApi = retrofit.create(CurrentWeatherApi.class);
    }

    public static CurrentWeatherRepository getInstance() {
        if (sInstance == null) {
            synchronized (CurrentWeatherRepository.class) {
                if (sInstance == null) {
                    sInstance = new CurrentWeatherRepository();
                }
            }
        }
        return sInstance;
    }

    public void getCurrentWeatherByLocation(double lat, double lon) {
        String query = String.valueOf(lat) + "," + String.valueOf(lon);
        Call<CurrentWeather> call = currentWeatherApi.currentWeather(Constants.API_KEY, query);
        call.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {

            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

            }
        });
    }

    public void getCurrentWeatherByArea() {

    }

    public void getCurrentWeatherByPostalCode() {

    }

    public void getCurrentWeatherByIpAddress() {

    }
}
