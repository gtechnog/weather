package com.gtechnog.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gtechnog.weather.utils.TextUtils;
import com.gtechnog.weather.viewmodel.CurrentWeatherViewModel;

public class WeatherDetailFragment extends Fragment {

    public WeatherDetailFragment() {
    }

    static WeatherDetailFragment newInstance() {
        return new WeatherDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        TextView tempreature = view.findViewById(R.id.tempreature);
        TextView city = view.findViewById(R.id.city);

        // TODO: Improvement
        // TODO: this fragment's viewmodel itself should call for tempreature from apixu and shared model should provide only
        // TODO: location not the tempreature (Single Responsibility)
        CurrentWeatherViewModel sharedViewModel = ViewModelProviders.of(getActivity()).get(CurrentWeatherViewModel.class);
        tempreature.setText(TextUtils.displayTempreatureString(sharedViewModel.getCurrentWeather().getCurrent().getTempInCelsius()));
        city.setText(TextUtils.displayCityString(sharedViewModel.getCurrentWeather().getLocation()));
        return view;
    }
}
