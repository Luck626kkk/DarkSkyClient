package com.example.luck.darkskyclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luck.darkskyclient.event.ErrorEvent;
import com.example.luck.darkskyclient.event.WeatherEvent;
import com.example.luck.darkskyclient.services.WeatherService;
import com.example.luck.darkskyclient.services.WeatherServiceProvider;
import com.example.luck.darkskyclient.until.WeatherIconUntil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import models.Currently;
import models.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

   // @BindView(R.id.tempTextView)
    TextView tempTextView;
    TextView summaryTextView;
    ImageView iconImageView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempTextView = (TextView) findViewById(R.id.tempTextView);
        summaryTextView = (TextView) findViewById(R.id.summaryTextView);
        iconImageView = (ImageView) findViewById(R.id.iconImageView);
        requestCurrentWeather(25.033964,121.564472);
        ButterKnife.bind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherEvent(WeatherEvent weatherEvent){
        Currently currently = weatherEvent.getWeather().getCurrently();
        Double aaa = (currently.getTemperature()-32)*5/9;
        //tempTextView.setText(String.valueOf(Math.round(currently.getTemperature())));
        tempTextView.setText(String.valueOf(Math.round(aaa)));
        summaryTextView.setText(currently.getSummary());
        iconImageView.setImageResource(WeatherIconUntil.ICONS.get(currently.getIcon()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorMessage(ErrorEvent errorEvent){
        Toast.makeText(this,errorEvent.getErrorMessage(),Toast.LENGTH_SHORT).show();
    }

    private void requestCurrentWeather(double lat, double lng) {
        WeatherServiceProvider weatherServiceProvider = new WeatherServiceProvider();
        weatherServiceProvider.getWeather(lat,lng);
    }
}
