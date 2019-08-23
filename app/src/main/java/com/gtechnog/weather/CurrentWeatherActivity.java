package com.gtechnog.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gtechnog.apixu.repository.CurrentWeatherRepository;

public class CurrentWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);
    }
}
