package com.gtechnog.apixu.models.forecast;

import com.google.gson.annotations.SerializedName;

public class Day {

    @SerializedName("maxtemp_c")
    private double maxTempInCelcius;

    @SerializedName("maxtemp_f")
    private double maxTempInFarhenhite;

    @SerializedName("mintemp_c")
    private double minTempInCelcius;

    @SerializedName("mintemp_f")
    private double minTempInFarhenhite;

    @SerializedName("avgtemp_c")
    private double averageTempInCelcius;

    @SerializedName("avgtemp_f")
    private double averageTempInFarhenhite;

}