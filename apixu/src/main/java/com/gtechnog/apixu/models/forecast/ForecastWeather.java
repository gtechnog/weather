package com.gtechnog.apixu.models.forecast;

import com.google.gson.annotations.SerializedName;
import com.gtechnog.apixu.models.common.Alert;
import com.gtechnog.apixu.models.common.Current;
import com.gtechnog.apixu.models.common.Location;

public class ForecastWeather {

    @SerializedName("location")
    private Location location;

    @SerializedName("current")
    private Current current;

    @SerializedName("forecast")
    private Forecast forecast;

    @SerializedName("alert")
    private Alert alert;

    public Forecast getForecast() {
        return forecast;
    }
}
