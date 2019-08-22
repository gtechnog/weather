package com.gtechnog.apixu.models.common;

import com.google.gson.annotations.SerializedName;

public class Current {

    @SerializedName("last_updated_epoch")
    private long lastUpdatedEpoch;

    @SerializedName("temp_c")
    private double tempInCelsius;

    @SerializedName("temp_f")
    private double tempInFahrenheit;

}
