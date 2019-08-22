package com.gtechnog.apixu.models.current;

import com.google.gson.annotations.SerializedName;
import com.gtechnog.apixu.models.common.Current;
import com.gtechnog.apixu.models.common.Location;

public class CurrentWeather {

    @SerializedName("location")
    private Location location;

    @SerializedName("current")
    private Current current;
}
