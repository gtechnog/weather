package com.gtechnog.weather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Factory class for creating RetryViewModle
 */
public class RetryViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final Application application;

    /**
     * Creates a {@code AndroidViewModelFactory}
     *
     * @param application an application to pass in {@link androidx.lifecycle.AndroidViewModel}
     */
    public RetryViewModelFactory(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RetryViewModel(application);
    }
}
