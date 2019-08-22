package com.gtechnog.apixu.models.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Forecast {

    @SerializedName("forecastday")
    private ArrayList<ForecastDay> forecastDay;

}
