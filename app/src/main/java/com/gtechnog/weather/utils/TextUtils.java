package com.gtechnog.weather.utils;

import com.gtechnog.apixu.models.common.Location;

public class TextUtils {

    // TODO: unit testing
    // TODO: 1.  28.2 should round to 28
    // TODO: 2.  28.8 should round to 29
    // TODO: 3.  test negative values
    public static String displayTempreatureString(double tempInCelsius) {
        return Math.round(tempInCelsius) + "°";
    }

    // TODO: unit testing
    // TODO: multiple combination with name, region, country, tz_id
    public static String displayCityString(Location location) {
        String displayString = "";
        if (location != null) {
            if (location.getName() != null && !location.getName().isEmpty()) {
                displayString = location.getName();
            } else if (location.getRegion() != null && !location.getRegion().isEmpty()) {
                displayString = location.getRegion();
            } else if (location.getCountry() != null && !location.getCountry().isEmpty()) {
                displayString = location.getCountry();
            }else if (location.getTzId() != null && !location.getTzId().isEmpty()) {
                displayString = location.getTzId();
            }
        }
        return displayString;
    }
}
