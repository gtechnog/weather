package com.gtechnog.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gtechnog.weather.utils.InjectorUtils;
import com.gtechnog.weather.viewmodel.CurrentWeatherViewModel;
import com.gtechnog.weather.viewmodel.RetryViewModel;

public class RetryFragment extends Fragment {

    private RetryViewModel viewModel;
    private CurrentWeatherViewModel sharedViewModel;

    public RetryFragment() {
    }

    static RetryFragment newInstance() {
        return new RetryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,
                InjectorUtils.createRetryViewModelFactory(getActivity().getApplication())).
                get(RetryViewModel.class);
        sharedViewModel = ViewModelProviders.of(this.getActivity()).get(CurrentWeatherViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retry, container, false);
        Button button = view.findViewById(R.id.retry_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.retryAndFetchAgain();
            }
        });
        return view;
    }
}
