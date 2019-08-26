package com.gtechnog.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gtechnog.apixu.models.forecast.Forecast;
import com.gtechnog.weather.adapters.ForecastAdapter;
import com.gtechnog.weather.utils.InjectorUtils;
import com.gtechnog.weather.viewmodel.CurrentWeatherViewModel;
import com.gtechnog.weather.viewmodel.ForecastViewModel;

public class ForecastFragment extends Fragment {

    private ForecastViewModel viewModel;
    private CurrentWeatherViewModel sharedViewModel;
    private RecyclerView recyclerView;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,
                InjectorUtils.createForecastViewModelFactory(getActivity().getApplication())).
                get(ForecastViewModel.class);
        sharedViewModel = ViewModelProviders.of(getActivity()).get(CurrentWeatherViewModel.class);
        viewModel.getForecastDetails(sharedViewModel.getCurrentLocation());
    }

    static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            subscribeUI();
        }
        return view;
    }

    private void subscribeUI() {
        viewModel.getForecast().observe(this, new Observer<Forecast>() {
            @Override
            public void onChanged(Forecast forecast) {
                if (forecast != null) {
                    recyclerView.setAdapter(new ForecastAdapter(forecast.getForecastDay()));
                }
            }
        });
    }
}
