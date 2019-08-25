package com.gtechnog.apixu.repository;

import com.gtechnog.apixu.models.forecast.ForecastWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastRepository {

    private static ForecastRepository sInstance;
    private final ForecastApi forecastApi;

    private ForecastRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        forecastApi = retrofit.create(ForecastApi.class);
    }
    
    public static ForecastRepository getInstance() {
        if (sInstance == null) {
            synchronized (ForecastRepository.class) {
                if (sInstance == null) {
                    sInstance = new ForecastRepository();
                }
            }

        }
        return sInstance;
    }

    public void getForecastByDays(double lattitude,
                                  double longitude,
                                  int days,
                                  final OnNetworkResponse<ForecastWeather> responseLisener) {
        String query = lattitude + "," + longitude;
        Call<ForecastWeather> call =  forecastApi.getForecastWeather(Constants.API_KEY, query, days);
        call.enqueue(new Callback<ForecastWeather>() {
            @Override
            public void onResponse(Call<ForecastWeather> call, Response<ForecastWeather> response) {
                if (responseLisener != null) {
                    responseLisener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ForecastWeather> call, Throwable t) {
                if (responseLisener != null) {
                    responseLisener.onFailure();
                }
            }
        });
    }

    public void getForecastByRestrictDate() {

    }

    public void getForcastByHour() {

    }
}
