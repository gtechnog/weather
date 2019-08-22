package com.gtechnog.apixu.repository;

public class ForecastRepository {

    private static ForecastRepository sInstance;

    private ForecastRepository() {
    }
    
    public static ForecastRepository getInstance() {
        if (sInstance == null) {
            synchronized (ForecastRepository.class) {
                if (sInstance == null) {
                    sInstance = new ForecastRepository();
                }
            }

        }
        return sInstance;
    }

    public void getForecastByDays() {

    }

    public void getForecastByRestrictDate() {

    }

    public void getForcastByHour() {

    }
}
