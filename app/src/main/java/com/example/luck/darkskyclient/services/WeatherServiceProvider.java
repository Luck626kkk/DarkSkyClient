package com.example.luck.darkskyclient.services;

import android.util.Log;

import com.example.luck.darkskyclient.event.ErrorEvent;
import com.example.luck.darkskyclient.event.WeatherEvent;

import org.greenrobot.eventbus.EventBus;

import models.Currently;
import models.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Luck on 2018/1/8.
 */

public class WeatherServiceProvider {
    private static final String BASE_URL =  "https://api.darksky.net/forecast/fffae99e325f21001d8cc53d67d77d5d/";
    private static final String TAG = WeatherServiceProvider.class.getSimpleName();
    private Retrofit retrofit;

   private Retrofit getRetrofit(){

        if(this.retrofit == null){
            this.retrofit =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return this.retrofit;
    }

    public void getWeather(double lat, double lng){
        WeatherService weatherService = getRetrofit().create(WeatherService.class);
        Call<Weather> WeatherData = weatherService.getWeather(lat,lng);

        WeatherData.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();
                if(weather != null) {

                   Currently currently = weather.getCurrently();
                   Log.e(TAG, "Temperature = " + currently.getTemperature());
                   EventBus.getDefault().post(new WeatherEvent(weather));
               }else{
                   Log.e(TAG,"No response : check secret key");
                   EventBus.getDefault().post(new ErrorEvent("No weather data available"));
               }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG,"onFailure = Unable to get weather data");
                EventBus.getDefault().post(new ErrorEvent("Unable ro connect to weather server"));
            }
        });

    }


}
