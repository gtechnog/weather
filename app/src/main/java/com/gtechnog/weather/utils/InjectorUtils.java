package com.gtechnog.weather.utils;

import android.app.Application;
import android.content.Context;

import com.gtechnog.apixu.repository.CurrentWeatherRepository;
import com.gtechnog.apixu.repository.ForecastRepository;
import com.gtechnog.weather.network.GoogleLocationApi;
import com.gtechnog.weather.viewmodel.CurrentWeatherViewModelFactory;
import com.gtechnog.weather.viewmodel.ForecastViewModelFactory;
import com.gtechnog.weather.viewmodel.RetryViewModelFactory;


/**
 * Dependency Injection Mechanism
 * TODO: this should be replace with Dagger2
 * Injection class for injecting viewModel, viewModelFactories, Repositories
 */
public class InjectorUtils {

    public static CurrentWeatherViewModelFactory createCurrentWeatherViewModelFactory(Application application) {
        return new CurrentWeatherViewModelFactory(application,
                getCurrentWeatherRepository(),
                getGoogleApiRepository(application));
    }

    public static ForecastViewModelFactory createForecastViewModelFactory(Application application) {
        return new ForecastViewModelFactory(application, getForecastRepository());
    }

    public static RetryViewModelFactory createRetryViewModelFactory(Application application) {
        return new RetryViewModelFactory(application);
    }

    private static ForecastRepository getForecastRepository() {
        return ForecastRepository.getInstance();
    }

    private static GoogleLocationApi getGoogleApiRepository(Context application) {
        return new GoogleLocationApi(application);
    }

    private static CurrentWeatherRepository getCurrentWeatherRepository() {
        return CurrentWeatherRepository.getInstance();
    }
}
