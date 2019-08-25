package com.gtechnog.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gtechnog.weather.adapters.ForecastAdapter;
import com.gtechnog.weather.utils.InjectorUtils;
import com.gtechnog.weather.utils.TextUtils;
import com.gtechnog.weather.viewmodel.CurrentWeatherViewModel;
import com.gtechnog.weather.viewmodel.ForecastViewModel;

public class ForecastFragment extends Fragment {

    private ForecastViewModel viewModel;
    private CurrentWeatherViewModel sharedViewModel;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,
                InjectorUtils.createForecastViewModelFactory(getActivity().getApplication())).
                get(ForecastViewModel.class);
        sharedViewModel = ViewModelProviders.of(getActivity()).get(CurrentWeatherViewModel.class);
        viewModel.getForecast(sharedViewModel.getCurrentLocation());
    }

    static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(new ForecastAdapter());
        }
        return view;
    }
}
