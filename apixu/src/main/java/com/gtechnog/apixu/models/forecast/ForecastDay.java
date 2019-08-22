package com.gtechnog.apixu.models.forecast;

import com.google.gson.annotations.SerializedName;

public class ForecastDay {

    @SerializedName ("date_epoch")
    private long dateEpoch;

    @SerializedName("day")
    private Day day;

    @SerializedName("astro")
    private Astro astro;
}
