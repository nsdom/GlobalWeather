package com.nsdom.globalweather.forecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.hourly.pojo.Hourly;
import com.nsdom.globalweather.forecast.hourly.pojo.HourlyWeather;
import com.nsdom.globalweather.forecast.network.OpenWeatherApi;
import com.nsdom.globalweather.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastActivity extends AppCompatActivity {

    private static final String TAG = "ForecastActivity";
    private BottomNavigationView  bottomNavigationView;
    private Context context = ForecastActivity.this;
    private ViewModel viewModel;
    private static final int MENU_ITEM = 0;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        setupWidgets();
        setupViewModel();
        setBottomNavigation();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherApi openWeatherApi = retrofit.create(OpenWeatherApi.class);
        Call<Hourly> call = openWeatherApi.getHourlyData(33.44, -94.04, "metric");
        call.enqueue(new Callback<Hourly>() {
            @Override
            public void onResponse(Call<Hourly> call, Response<Hourly> response) {

                Log.d(TAG, "onResponse: Server Response" + response.toString());
                assert response.body() != null;
                Log.d(TAG, "onResponse: Response: " + response.body().toString());
                ArrayList<HourlyWeather> hourlyWeather = response.body().getHourly();
                for (int i = 0; i < hourlyWeather.size(); i++) {
                    Log.d(TAG, "onResponse: \n" +
                            "hour: " + hourlyWeather.get(i).getHour() + "\n" +
                            "temperature: " + hourlyWeather.get(i).getTemperature() + "\n" +
                            "feels like: " + hourlyWeather.get(i).getFeelsLike() + "\n" +
                            "humidity: " + hourlyWeather.get(i).getHumidity() + "%\n" +
                            "clouds: " + hourlyWeather.get(i).getClouds() + "%\n" +
                            "wind speed: " + hourlyWeather.get(i).getWindSpeed() + "m/s\n" +
                            "wind degree: " + hourlyWeather.get(i).getWindDegree() + "degrees\n" +
                            "weather: \n-main: " + hourlyWeather.get(i).getWeather().get(0).getMain() + "\n" +
                            "-description: " + hourlyWeather.get(i).getWeather().get(0).getDescription() + "\n" +
                            "-icon: " + hourlyWeather.get(i).getWeather().get(0).getIcon() + "\n" +
                            "--------------------------------------------------------------------");
                }
            }

            @Override
            public void onFailure(Call<Hourly> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong" + t.getMessage());
            }
        });

   }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupViewModel() {
        viewModel = new ViewModel(context);
        ForecastModel forecastModel = new ForecastModel(context);
        forecastModel.setupViewPager(viewPager, tabLayout);
    }

    private void setupWidgets() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        viewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);
    }

    private void setBottomNavigation() {
        viewModel.enableNavigation(bottomNavigationView);
        viewModel.setMenuItemChecked(bottomNavigationView, MENU_ITEM);
    }
}
