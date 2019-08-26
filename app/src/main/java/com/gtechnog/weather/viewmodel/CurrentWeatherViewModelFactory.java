package com.gtechnog.weather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gtechnog.apixu.repository.CurrentWeatherRepository;
import com.gtechnog.weather.network.GoogleLocationApi;

/**
 * Factory class for creating {@link CurrentWeatherViewModel}
 */
public class CurrentWeatherViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final Application application;
    private final CurrentWeatherRepository currentWeatherRepository;
    private final GoogleLocationApi googleApiRepository;

    /**
     * Creates a {@code AndroidViewModelFactory}
     *
     * @param application an application to pass in {@link androidx.lifecycle.AndroidViewModel}
     * @param currentWeatherRepository current weather repository
     * @param googleApiRepository googleapi repository
     */
    public CurrentWeatherViewModelFactory(@NonNull Application application,
                                          @NonNull CurrentWeatherRepository currentWeatherRepository,
                                          @NonNull GoogleLocationApi googleApiRepository) {
        super(application);
        this.application = application;
        this.currentWeatherRepository =  currentWeatherRepository;
        this.googleApiRepository = googleApiRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CurrentWeatherViewModel(application,
                currentWeatherRepository,
                googleApiRepository);
    }
}
