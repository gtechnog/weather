package com.gtechnog.weather.viewmodel;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gtechnog.apixu.models.current.CurrentWeather;
import com.gtechnog.apixu.repository.CurrentWeatherRepository;
import com.gtechnog.apixu.repository.OnNetworkResponse;
import com.gtechnog.weather.network.GoogleLocationApi;
import com.gtechnog.weather.utils.PermissionUtils;

public class CurrentWeatherViewModel extends AndroidViewModel {


    private static final String TAG = CurrentWeatherViewModel.class.getSimpleName();
    public static final int STATE_INITIAL = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_SHOW_TEMP = 2;
    public static final int STATE_ERROR = 3;

    private final Application application;
    private final GoogleLocationApi googleApi;
    private final CurrentWeatherRepository currentWeatherRepository;

    private MutableLiveData<Integer> state = new MutableLiveData<>();
    private MutableLiveData<Boolean> shouldRequestForPermission = new MutableLiveData<>();

    private CurrentWeather currentWeather;

    private final GoogleLocationApi.ResponseListener responseListener;
    private Location currentLocation;

    CurrentWeatherViewModel(@NonNull Application application,
                            CurrentWeatherRepository currentWeatherRepository,
                            GoogleLocationApi googleApiRepository) {
        super(application);
        this.application = application;
        this.googleApi = googleApiRepository;
        this.currentWeatherRepository = currentWeatherRepository;
        getState().postValue(STATE_INITIAL);

        responseListener = getGoogleApiResponseListener();
        if (!PermissionUtils.isLocationPermissionGranted(application)) {
            shouldRequestForPermission.postValue(true);
        } else {
            getUserLocation();
        }
    }

    public void getUserLocation() {
        getState().postValue(STATE_LOADING);
        try {
            googleApi.fetchLocation(responseListener);
        } catch (GoogleLocationApi.LocationPermissionDenied locationPermissionDenied) {
            // TODO: Handle location permission
            shouldRequestForPermission.postValue(true);
        } catch (GoogleLocationApi.LocationNotEnabled locationNotEnabled) {
            // TODO: Handle Location is not enabled
        }
    }

    private GoogleLocationApi.ResponseListener getGoogleApiResponseListener() {
        return new GoogleLocationApi.ResponseListener() {
            @Override
            public void onLocationUpdate(Location location) {
                Log.d(TAG, "onLocationUpdate: " + " Lattitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
                currentLocation = location;
                getCurrentWeatherByLocation(location);
                googleApi.disconnect();
            }

            @Override
            public void onError() {
                Log.e(TAG, "onError: error in getting user location" );
                getState().postValue(STATE_ERROR);
                googleApi.disconnect();
            }
        };
    }

    private void getCurrentWeatherByLocation(Location location) {
        currentWeatherRepository.getCurrentWeatherByLocation(location.getLatitude(),
                location.getLongitude(),
                new OnNetworkResponse<CurrentWeather>() {
                    @Override
                    public void onSuccess(CurrentWeather weather) {
                        Log.d(TAG, "onSuccess: current weather received successfully");
                        currentWeather = weather;
                        getState().postValue(STATE_SHOW_TEMP);
                    }

                    @Override
                    public void onFailure() {
                        Log.e(TAG, "onFailure: error in getting current weather");
                        getState().postValue(STATE_ERROR);
                    }
                });
    }


    public MutableLiveData<Integer> getState() {
        return state;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public MutableLiveData<Boolean> getShouldRequestForPermission() {
        return shouldRequestForPermission;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }
}
