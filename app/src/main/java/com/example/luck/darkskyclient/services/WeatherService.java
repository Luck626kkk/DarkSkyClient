package com.example.luck.darkskyclient.services;

import models.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Luck on 2018/1/8.
 */

public interface WeatherService {
    @GET("{lat},{lng}")
    Call<Weather> getWeather(@Path("lat") double lat,@Path("lng") double lng);

}
