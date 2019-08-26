package com.gtechnog.weather.network;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gtechnog.weather.utils.PermissionUtils;

/**
 *  GoogleLocationApi responsible for fetching for user's current location. This api
 *  depends on location permission enabled for the app and location provider enabled.
 *  If these are not enabled it throws exception.
 *  To get the location from this api provide {@link ResponseListener}
 *
 *  TODO Improvement: currently this uses fusedLocationApi in some cases its returning null
 *  TODO: if its null then request for location update, that code is not working: BUG
 *
 */
public class GoogleLocationApi implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = GoogleLocationApi.class.getSimpleName();
    private final Context context;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private static ResponseListener listener;

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

    /**
     * @param responseListener  to listen location update
     * @throws LocationPermissionDenied if location permission is not granted for the app
     * @throws LocationNotEnabled if location provider is not enabled
     */
    public void fetchLocation(ResponseListener responseListener) throws LocationPermissionDenied, LocationNotEnabled{
        listener = responseListener;

        // TODO Improvement: if google play services not available, figure out other way to get location
        if (!checkIfGooglePlayServicesAvailable()) {
            // TODO: Handle by providing proper message to user
            listener.onError();
        }

        if (!checkIfLocationEnabled()) {
            // TODO: throwing exception
            throw new LocationNotEnabled();
        }

        if (!PermissionUtils.isLocationPermissionGranted(context)) {
            // TODO: throwing exception
            throw new LocationPermissionDenied();
        }

        if (!googleApiClient.isConnected() && !googleApiClient.isConnecting()) {
            googleApiClient.connect();
        }
    }

    /**
     * disconnect the googleApiClient once you are done
     */
    public void disconnect() {
        Log.d(TAG, "google location api disconnect() called");
        googleApiClient.disconnect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "google client api onConnected: ");
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);
        client.getLastLocation().
                addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        // TODO: currently redirecting to onError();
                        if (listener != null)
                            listener.onError();
                    }})
                .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (listener != null)
                                listener.onError();
                    }})
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                if (listener != null) {
                                    listener.onLocationUpdate(location);
                                }
                            } else {
                                Log.d(TAG, "onSuccess: location null");
                                client.requestLocationUpdates(locationRequest, new MyLocationCallback(), null);
                            }
                        }
                    });
                }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: ");
        // TODO: Handle this
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: ");
        if (listener != null) {
            listener.onError();
        }
    }


    /**
     * @return true if google services available
     */
    private boolean checkIfGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            // TODO: Handle this google services availability by showing dialog to user
            // TODO: this can be resolve
            Log.d(TAG, "Google Play services is not available for this device");
            return false;
        }
        Log.d(TAG, "This device supports google play services.");
        return true;
    }

    /**
     * @return true if location provider is enabled
     */
    private boolean checkIfLocationEnabled() {
        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * Exception if location permission is denied for the application
     */
    public static class LocationPermissionDenied extends Exception {
        LocationPermissionDenied() {
        }
    }

    /**
     * Exception if location provider is not enabled
     */
    public class LocationNotEnabled extends Exception {
        LocationNotEnabled() {
        }
    }


    /**
     * Location callback if fusedLocationApi is returning null as last location
     * TODO: currently this part of code is not working BUG:
     */
    static class MyLocationCallback extends LocationCallback {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Log.d(TAG, "onLocationResult: " + locationResult);
            if (locationResult == null) {
                listener.onError();
                return;
            }
            listener.onLocationUpdate(locationResult.getLocations().get(0));
        }

        MyLocationCallback() {
            super();
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }
    }
}
