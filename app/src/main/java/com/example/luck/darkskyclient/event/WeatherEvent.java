package com.example.luck.darkskyclient.event;

import models.Weather;

/**
 * Created by Luck on 2018/1/8.
 */

public class WeatherEvent {
    private final Weather weather;

    public WeatherEvent(Weather weather) {
        this.weather=weather;
    }
    public Weather getWeather(){
        return weather;
    }

}
