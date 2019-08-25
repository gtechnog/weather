package com.gtechnog.weather.network;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class GoogleLocationApi implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = GoogleLocationApi.class.getSimpleName();
    private final Context context;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private ResponseListener listener;

    public interface ResponseListener {
        void onLocationUpdate(Location location);
        void onError();
    }

    public GoogleLocationApi(Context context) {
        this.context = context;
        googleApiClient = new GoogleApiClient.Builder(context).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API)
                .build();

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }

    public void fetchLocation(ResponseListener responseListener) throws LocationPermissionDenied, LocationNotEnabled{
        listener = responseListener;

        if (!checkIfGooglePlayServicesAvailable()) {
            // TODO: Handle by providing proper message to user
            listener.onError();
        }

        if (!checkIfLocationEnabled()) {
            // TODO: throwing exception
            throw new LocationNotEnabled("Location is not enabled from Settings");
        }

        if (!checkIfPermissionGranted() ) {
            // TODO: throwing exception
            throw new LocationPermissionDenied("Location permission is not granted");
        }

        if (!googleApiClient.isConnected() && !googleApiClient.isConnecting()) {
            googleApiClient.connect();
        }
    }

    public void disconnect() {
        Log.d(TAG, "google location api disconnect() called");
        googleApiClient.disconnect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "google client api onConnected: ");
        Task<Location> locationTask = LocationServices.getFusedLocationProviderClient(context).getLastLocation();
        locationTask.addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                // TODO: currently redirecting to onError();
                if (listener != null) {
                    listener.onError();
                }
            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (listener != null) {
                    listener.onError();
                }
            }
        });
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (listener != null) {
                    listener.onLocationUpdate(location);
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: ");
        if (listener != null) {
            listener.onError();
        }
    }

    private boolean checkIfPermissionGranted() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkIfGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            // TODO: Handle this google services availability by showing dialog to user
            Log.d(TAG, "Google Play services is not available for this device");
            return false;
        }
        Log.d(TAG, "This device supports google play services.");
        return true;
    }

    private boolean checkIfLocationEnabled() {
        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static class LocationPermissionDenied extends Exception {
        LocationPermissionDenied(String permission) {
        }
    }

    public class LocationNotEnabled extends Exception {
        LocationNotEnabled(String message) {
        }
    }
}
