package com.gtechnog.weather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gtechnog.apixu.repository.ForecastRepository;

/**
 * Factory class for creating {@link ForecastViewModel}
 */
public class ForecastViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final ForecastRepository forecastRepository;
    private final Application application;

    /**
     * Creates a {@code AndroidViewModelFactory}
     * @param forecastRepository forecast repository
     * @param application an application to pass in {@link androidx.lifecycle.AndroidViewModel}
     */

    public ForecastViewModelFactory(Application application, ForecastRepository forecastRepository) {
        super(application);
        this.application = application;
        this.forecastRepository = forecastRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ForecastViewModel(application, forecastRepository);
    }
}
