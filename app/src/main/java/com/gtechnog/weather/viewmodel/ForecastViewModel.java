package com.gtechnog.weather.viewmodel;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gtechnog.apixu.models.forecast.Forecast;
import com.gtechnog.apixu.models.forecast.ForecastWeather;
import com.gtechnog.apixu.repository.ForecastRepository;
import com.gtechnog.apixu.repository.OnNetworkResponse;
import com.gtechnog.weather.constants.Constants;

public class ForecastViewModel extends AndroidViewModel {

    private static final String TAG = ForecastViewModel.class.getSimpleName();
    private final Application application;
    private final ForecastRepository forecastRepository;
    private MutableLiveData<Forecast> forecast = new MutableLiveData<>();

    ForecastViewModel(@NonNull Application application, ForecastRepository forecastRepository) {
        super(application);
        this.application = application;
        this.forecastRepository = forecastRepository;
    }

    /**
     *
     * @param location location of which we need the forecast
     */
    public void getForecast(Location location) {
        if (location != null) {
            forecastRepository.getForecastByDays(location.getLatitude(), location.getLongitude(), Constants.FORECAST_DAYS,
                    new OnNetworkResponse<ForecastWeather>() {
                        @Override
                        public void onSuccess(ForecastWeather forecastWeather) {
                            Log.d(TAG, "onSuccess: received forecast successfully");
                            forecast.postValue(forecastWeather.getForecast());
                        }

                        @Override
                        public void onFailure() {
                            Log.d(TAG, "onFailure: failed in receiving forecast");
                            // TODO: Handle forecast error state
                        }
                    });
        }
    }

    public MutableLiveData<Forecast> getForecast() {
        return forecast;
    }
}
