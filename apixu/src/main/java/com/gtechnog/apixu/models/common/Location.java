package com.gtechnog.apixu.models.common;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("name")
    private String name;

    @SerializedName("region")
    private String region;

    @SerializedName("country")
    private String country;

    @SerializedName("lat")
    private double latitude;

    @SerializedName("lon")
    private double longitude;

    @SerializedName("tz_id")
    private String tzId;
}
