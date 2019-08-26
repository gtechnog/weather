package com.gtechnog.weather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * ViewModel that is tied with {@link com.gtechnog.weather.RetryFragment}
 */
public class RetryViewModel extends AndroidViewModel {
    RetryViewModel(@NonNull Application application) {
        super(application);
    }
}
