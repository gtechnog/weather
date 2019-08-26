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

/**
 *  ViewModel tied to {@link com.gtechnog.weather.CurrentWeatherActivity} and responsible for
 *  getting data for the UI
 */
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
    private Location currentLocation;

    CurrentWeatherViewModel(@NonNull Application application,
                            CurrentWeatherRepository currentWeatherRepository,
                            GoogleLocationApi googleApiRepository) {
        super(application);
        this.application = application;
        this.googleApi = googleApiRepository;
        this.currentWeatherRepository = currentWeatherRepository;
        getState().postValue(STATE_INITIAL);

        if (!PermissionUtils.isLocationPermissionGranted(application)) {
            shouldRequestForPermission.postValue(true);
        } else {
            getUserLocation();
        }
    }

    /**
     *  call this method to get the user location and start fetching the
     *  temperature for this location.
     *  If location permission is not granted this will update the mutable data and UI must
     *  observe that and ask user for permission
     *  // TODO TASK: Locatoin provider enable/disable permission is not handled
     */
    public void getUserLocation() {
        getState().postValue(STATE_LOADING);
        try {
            googleApi.fetchLocation(getGoogleApiResponseListener());
        } catch (GoogleLocationApi.LocationPermissionDenied locationPermissionDenied) {
            // TODO: Handle location permission
            shouldRequestForPermission.postValue(true);
        } catch (GoogleLocationApi.LocationNotEnabled locationNotEnabled) {
            // TODO: Handle Location is not enabled
        }
    }


    /**
     *  Retry of fetching location
     */
    public void retryAndFetchAgain() {
        getUserLocation();
    }

    private GoogleLocationApi.ResponseListener getGoogleApiResponseListener() {
        return new GoogleLocationApi.ResponseListener() {
            @Override
            public void onLocationUpdate(Location location) {
                disconnectGoogleApi();
                if (location == null) {
                    getState().postValue(STATE_ERROR);
                    return;
                }
                Log.d(TAG, "onLocationUpdate: " + " Lattitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
                currentLocation = location;
                getCurrentWeatherByLocation(location);
            }

            @Override
            public void onError() {
                Log.e(TAG, "onError: error in getting user location" );
                getState().postValue(STATE_ERROR);
                disconnectGoogleApi();
            }
        };
    }

    private void getCurrentWeatherByLocation(Location location) {
        currentWeatherRepository.getCurrentWeatherByLocation(location.getLatitude(),
                location.getLongitude(),
                new OnNetworkResponse<CurrentWeather>() {
                    @Override
                    public void onSuccess(CurrentWeather weather) {
                        Log.d(TAG, "onSuccess() called with: weather = [" + weather.getCurrent().getTempInCelsius() + "]");
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

    /**
     *  Disconnect google API
     */
    public void disconnectGoogleApi() {
        googleApi.disconnect();
    }

    /**
     * @return MutableLiveData of screen state, UI must observe this value
     * and update UI accordingly
     */
    public MutableLiveData<Integer> getState() {
        return state;
    }

    /**
     * @return {@link CurrentWeather}
     */
    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }


    /**
     * @return MutableLiveData of location permission for app, UI must observe this value
     * and ask user for location permission
     */
    public MutableLiveData<Boolean> getShouldRequestForPermission() {
        return shouldRequestForPermission;
    }


    /**
     * @return {@link Location}
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }
}
