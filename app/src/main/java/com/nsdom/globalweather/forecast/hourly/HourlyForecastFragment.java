package com.nsdom.globalweather.forecast.hourly;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.ForecastActivity;
import com.nsdom.globalweather.forecast.hourly.pojo.HourlyWeather;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecastFragment extends Fragment {

    private static final String TAG = "HourlyForecastFragment";
    private List<HourlyWeather> hourlyWeatherList = new ArrayList<>();
    private Context context = getActivity();
    private RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hourly_forecast, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        HourlyRecyclerAdapter adapter = new HourlyRecyclerAdapter(hourlyWeatherList, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        return view;
    }
}
