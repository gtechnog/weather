package com.gtechnog.weather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * // TODO Improvement: Tie this model to {@link com.gtechnog.weather.WeatherDetailFragment} and take the
 * // TODO Improvement: responsibility of getting tempreature detail from {@link CurrentWeatherViewModel}
 */
public class WeatherDetailViewModel extends AndroidViewModel {
    public WeatherDetailViewModel(@NonNull Application application) {
        super(application);
    }
}
