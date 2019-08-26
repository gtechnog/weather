package com.gtechnog.weather;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gtechnog.weather.utils.InjectorUtils;
import com.gtechnog.weather.utils.PermissionUtils;
import com.gtechnog.weather.viewmodel.CurrentWeatherViewModel;

public class CurrentWeatherActivity extends AppCompatActivity {

    private static final String TAG = CurrentWeatherActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;
    private static final String TAG_LOADING_FRAGMENT = "loading_fragment";
    private static final String TAG_FRAGMENT_RETRY = "retry_fragment";
    private static final String TAG_FRAGMENT_TEMPREATURE = "tempreature_fragment";
    private static final String TAG_FRAGMENT_FORECAST = "forecast_fragment";

    private CurrentWeatherViewModel viewModel;
    private ViewGroup currentWeatherDetailLayout;
    private ViewGroup forecastLayout;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);

        currentWeatherDetailLayout = findViewById(R.id.current_weather_container);
        forecastLayout = findViewById(R.id.forecast_container);


        // TODO: Inject this View model factory from Dagger
        viewModel = ViewModelProviders.of(this, InjectorUtils.createCurrentWeatherViewModelFactory(this.getApplication()))
                .get(CurrentWeatherViewModel.class);

        // TODO: BUG here its not working as per flow
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")){
                    viewModel.getUserLocation();
                }
            }
        };
        subscribeUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.disconnectGoogleApi();
        unregisterReceiver(broadcastReceiver);
    }

    private void subscribeUI() {
        viewModel.getState().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer state) {
                showFragment(state);
            }
        });
        viewModel.getShouldRequestForPermission().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldAskForPermission) {
                requestLocationPermission();
            }
        });
    }



    private void requestLocationPermission() {
        if (!PermissionUtils.isLocationPermissionGranted(getApplicationContext())) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: " + " location permission granted");
                viewModel.getUserLocation();
            } else {
                Log.i(TAG, "onRequestPermissionsResult: " + " location permission not granted");
            }
        }
    }



    /**
     * Remove all the attached fragments to this activity
     * TODO: Improvement: should have done without removing all the fragments
     */
    private void removeFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        Fragment retryFragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_RETRY);
        if (retryFragment != null) {
            fragmentTransaction.remove(retryFragment);
        }
        Fragment tempFragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_TEMPREATURE);
        if (tempFragment != null) {
            fragmentTransaction.remove(tempFragment);
        }
        Fragment forecastFragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_FORECAST);
        if (forecastFragment != null) {
            fragmentTransaction.remove(forecastFragment);
        }
        fragmentTransaction.commit();
    }

    /**
     * show fragment as per the state of the activity
     * @param state state of the screen
     */
    private void showFragment(Integer state) {
        removeFragments();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (state) {
            case CurrentWeatherViewModel.STATE_LOADING:
                currentWeatherDetailLayout.setVisibility(View.GONE);
                forecastLayout.setVisibility(View.GONE);
                fragmentTransaction.add(R.id.activity_root_container, LoadingFragment.newInstance(), TAG_LOADING_FRAGMENT);
                break;
            case CurrentWeatherViewModel.STATE_ERROR:
                currentWeatherDetailLayout.setVisibility(View.GONE);
                forecastLayout.setVisibility(View.GONE);
                fragmentTransaction.add(R.id.activity_root_container, RetryFragment.newInstance(), TAG_FRAGMENT_RETRY);
                break;
            case CurrentWeatherViewModel.STATE_SHOW_TEMP:
                currentWeatherDetailLayout.setVisibility(View.VISIBLE);
                forecastLayout.setVisibility(View.VISIBLE);
                fragmentTransaction.add(R.id.current_weather_container, WeatherDetailFragment.newInstance(), TAG_FRAGMENT_TEMPREATURE);
                fragmentTransaction.add(R.id.forecast_container, ForecastFragment.newInstance(), TAG_FRAGMENT_FORECAST);
                break;
                default:
                    break;
        }
        fragmentTransaction.commit();
    }
}
