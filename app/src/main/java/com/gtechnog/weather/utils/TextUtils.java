package com.gtechnog.weather.utils;

import com.gtechnog.apixu.models.common.Location;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 *  Utility class for displaying text
 */
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

    // TODO: move these strings to resources file
    public static String displayDayString(long dateEpoch) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dateEpoch * 1000);

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
                default:
                    return "";
        }
    }

    public static String displayForecastTempString(double tempInCelsius) {
        return Math.round(tempInCelsius) + " C";
    }
}
