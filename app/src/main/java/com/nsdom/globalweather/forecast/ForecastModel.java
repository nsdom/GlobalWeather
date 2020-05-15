package com.nsdom.globalweather.forecast;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.tabs.TabLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.current.CurrentForecastFragment;
import com.nsdom.globalweather.forecast.daily.DailyForecastFragment;
import com.nsdom.globalweather.forecast.daily.DailyRecyclerAdapter;
import com.nsdom.globalweather.forecast.hourly.HourlyForecastFragment;
import com.nsdom.globalweather.forecast.hourly.HourlyRecyclerAdapter;
import com.nsdom.globalweather.forecast.pojo.Daily;
import com.nsdom.globalweather.forecast.pojo.DailyWeather;
import com.nsdom.globalweather.forecast.pojo.Hourly;
import com.nsdom.globalweather.forecast.pojo.HourlyWeather;
import com.nsdom.globalweather.forecast.network.OpenWeatherApi;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastModel {

    private static final int BEHAVIOR = 1;
    private final Context context;
    private static final String TAG = "ForecastModel";

    public ForecastModel(Context context) {
        this.context = context;
    }

    void setupViewPager(ViewPager viewPager, TabLayout tabLayout) {
        PagerAdapter pagerAdapter = new PagerAdapter(((ForecastActivity) context).getSupportFragmentManager(), BEHAVIOR);
        pagerAdapter.addFragment(new CurrentForecastFragment());
        pagerAdapter.addFragment(new HourlyForecastFragment());
        pagerAdapter.addFragment(new DailyForecastFragment());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_current);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.ic_hourly);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.ic_daily);
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void fetchHourlyData(RecyclerView recyclerView, GraphView graphView) {
        Retrofit retrofit = getRetrofit();
        OpenWeatherApi openWeatherApi = retrofit.create(OpenWeatherApi.class);
        Call<Hourly> call = openWeatherApi.getHourlyData(39.9163, -8.9520, "metric");
        //noinspection NullableProblems
        call.enqueue(new Callback<Hourly>() {
            @Override
            public void onResponse(Call<Hourly> call, Response<Hourly> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                assert response.body() != null;
                Log.d(TAG, "onResponse: Response: " + response.body().toString());
                ArrayList<HourlyWeather> hourlyWeathers = response.body().getHourly();
                setupHourlyRecyclerView(response, hourlyWeathers, recyclerView);
                setupGraphView(hourlyWeathers, graphView);
            }
            @Override
            public void onFailure(Call<Hourly> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong" + t.getMessage());
            }
        });
    }

    public void fetchDailyData(RecyclerView recyclerView, LineChart lineChart) {
        Retrofit retrofit = getRetrofit();
        OpenWeatherApi api = retrofit.create(OpenWeatherApi.class);
        Call<Daily> call = api.getDailyData(39.9163, -8.9520, "metric");
        call.enqueue(new Callback<Daily>() {
            @Override
            public void onResponse(Call<Daily> call, Response<Daily> response) {
                Log.d(TAG, "onResponse: Server Response: " + response.toString());
                assert response.body() != null;
                Log.d(TAG, "onResponse: Response" + response.body().toString());
                ArrayList<DailyWeather> dailyWeathers = response.body().getDaily();
                setupDailyRecyclerView(response, dailyWeathers, recyclerView);
            }
            @Override
            public void onFailure(Call<Daily> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong", t);
            }
        });
    }

    private void setupHourlyRecyclerView(Response<Hourly> response, ArrayList<HourlyWeather> hourlyWeathers, RecyclerView recyclerView) {
        assert response.body() != null;
        String timezone = response.body().getTimezone();
        HourlyRecyclerAdapter adapter = new HourlyRecyclerAdapter(hourlyWeathers, timezone);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupDailyRecyclerView(Response<Daily> response, ArrayList<DailyWeather> dailyWeathers, RecyclerView recyclerView) {
        assert response.body() != null;
        String timezone = response.body().getTimezone();
        DailyRecyclerAdapter adapter = new DailyRecyclerAdapter(dailyWeathers, timezone);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupGraphView(ArrayList<HourlyWeather> hourlyWeathers, GraphView graphView) {
        DataPoint[] dataPoints = new DataPoint[24];
        Double max = Double.MIN_VALUE;
        Double min = Double.MAX_VALUE;
        for (int i = 0; i < 24; i++) {
            DataPoint v = new DataPoint(i, hourlyWeathers.get(i).getTemperature());
            dataPoints[i] = v;
            if (hourlyWeathers.get(i).getTemperature() > max) max = hourlyWeathers.get(i).getTemperature();
            if (hourlyWeathers.get(i).getTemperature() < min) min = hourlyWeathers.get(i).getTemperature();
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        series.setColor(Color.BLACK);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMaxX(24);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMaxY(max + 5);
        graphView.getViewport().setMinY(min > 0 ? 0 : min);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(5);
        graphView.getGridLabelRenderer().setNumVerticalLabels(10);
        graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graphView.getGridLabelRenderer().setLabelsSpace(10);
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("         Hours");
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Temperature");
        graphView.addSeries(series);
    }
}
