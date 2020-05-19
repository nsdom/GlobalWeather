package com.nsdom.globalweather.forecast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.tabs.TabLayout;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.marcinmoskala.arcseekbar.ArcSeekBar;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.current.CurrentForecastFragment;
import com.nsdom.globalweather.forecast.daily.DailyForecastFragment;
import com.nsdom.globalweather.forecast.daily.DailyRecyclerAdapter;
import com.nsdom.globalweather.forecast.hourly.HourlyForecastFragment;
import com.nsdom.globalweather.forecast.hourly.HourlyRecyclerAdapter;
import com.nsdom.globalweather.forecast.pojo.Current;
import com.nsdom.globalweather.forecast.pojo.CurrentWeather;
import com.nsdom.globalweather.forecast.pojo.Daily;
import com.nsdom.globalweather.forecast.pojo.DailyWeather;
import com.nsdom.globalweather.forecast.pojo.Hourly;
import com.nsdom.globalweather.forecast.pojo.HourlyWeather;
import com.nsdom.globalweather.forecast.pojo.Location;
import com.nsdom.globalweather.locations.pojo.HereGeocoderResponse;
import com.nsdom.globalweather.locations.pojo.HereSearchResponse;
import com.nsdom.globalweather.locations.pojo.ResponsePosition;
import com.nsdom.globalweather.locations.utils.ArrayAdapterNoSpaceFilter;
import com.nsdom.globalweather.network.HereApi;
import com.nsdom.globalweather.network.OpenWeatherApi;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

    public void fetchCurrentData(View view, Location coordinates) {
        Retrofit retrofit = getRetrofit();
        OpenWeatherApi openWeatherApi = retrofit.create(OpenWeatherApi.class);
        Call<Current> call = openWeatherApi.getCurrentData(coordinates.getLatitude(), coordinates.getLongitude(), "metric");
        //noinspection NullableProblems
        call.enqueue(new Callback<Current>() {
            @Override
            public void onResponse(Call<Current> call, Response<Current> response) {
                Log.d(TAG, "onResponse: Server Response: " + response.toString());
                assert response.body() != null;
                Log.d(TAG, "onResponse: Response: " + response.body().toString());
                CurrentWeather currentWeather = response.body().getCurrent();
                String timezone = response.body().getTimezone();
                setupViewWidgets(view, currentWeather, timezone);

            }
            @Override
            public void onFailure(Call<Current> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong", t);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setupViewWidgets(View view, CurrentWeather currentWeather, String timezone) {
        TextView temperatureTxtView = view.findViewById(R.id.temperature_textView);
        TextView timeTxtView = view.findViewById(R.id.date_textView);
        TextView descriptionTxtView = view.findViewById(R.id.description_txt_view);
        TextView windTextView = view.findViewById(R.id.wind_textView);
        ImageView windDirectionImgView = view.findViewById(R.id.wind_direction_img_view);
        TextView cloudsTxtView = view.findViewById(R.id.clouds_textView);
        TextView humidityTxtView = view.findViewById(R.id.humidity_textView);
        TextView feelsLikeTxtView = view.findViewById(R.id.feels_like_textView);
        ImageView iconDescriptionTxtView = view.findViewById(R.id.iconDescription_img_view);
        TextView sunriseTxtView = view.findViewById(R.id.sunriseTxtView);
        TextView sunsetTextView = view.findViewById(R.id.sunsetTxtView);
        ArcSeekBar arcSeekBar = view.findViewById(R.id.arc_seek_bar);
        arcSeekBar.setProgressGradient(Color.YELLOW, Color.RED, Color.GRAY);
        long currentTime = currentWeather.getTime();
        long sunrise = currentWeather.getSunrise();
        long sunset = currentWeather.getSunset();
        int progress = Math.round(((currentTime - sunrise) * 1.00f) / ((sunset - sunrise) * 1.0f) * 100);
        arcSeekBar.setProgress(progress);
        arcSeekBar.setEnabled(false);
        Log.d(TAG, "setupViewWidgets: Progress: " + progress);

        String temperature = Long.toString(Math.round(currentWeather.getTemperature()));
        String description = currentWeather.getWeather().get(0).getDescription();
        String windSpeed = currentWeather.getWindSpeed().toString();
        int rotation = currentWeather.getWindDegree();
        String clouds = Integer.toString(currentWeather.getClouds());
        String humidity = Integer.toString(currentWeather.getHumidity());
        String feelsLike = Long.toString(Math.round(currentWeather.getFeelsLike()));
        String iconString = currentWeather.getWeather().get(0).getIcon();
        String iconUrl = "https://openweathermap.org/img/wn/" + iconString + "@2x.png";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a, MMM d", Locale.US);
        SimpleDateFormat sdfSeek = new SimpleDateFormat("HH:mm a", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        String time = sdf.format(currentWeather.getTime() * 1000L);
        sdfSeek.setTimeZone(TimeZone.getTimeZone(timezone));
        String sunriseTime = sdfSeek.format(sunrise * 1000L);
        String sunsetTime = sdfSeek.format(sunset * 1000L);

        temperatureTxtView.setText(temperature);
        descriptionTxtView.setText(description);
        windTextView.setText(windSpeed);
        windDirectionImgView.setRotation(rotation);
        cloudsTxtView.setText(clouds);
        humidityTxtView.setText(humidity);
        feelsLikeTxtView.setText(feelsLike);
        Picasso.get().load(iconUrl).into(iconDescriptionTxtView);
        timeTxtView.setText(time);
        sunriseTxtView.setText(sunriseTime);
        sunsetTextView.setText(sunsetTime);

    }


    public void fetchHourlyData(RecyclerView recyclerView, LineChart lineChart, Location coordinates) {
        Retrofit retrofit = getRetrofit();
        OpenWeatherApi openWeatherApi = retrofit.create(OpenWeatherApi.class);
        Call<Hourly> call = openWeatherApi.getHourlyData(coordinates.getLatitude(), coordinates.getLongitude(), "metric");
        //noinspection NullableProblems
        call.enqueue(new Callback<Hourly>() {
            @Override
            public void onResponse(Call<Hourly> call, Response<Hourly> response) {
                Log.d(TAG, "onResponse: Server Response" + response.toString());
                assert response.body() != null;
                Log.d(TAG, "onResponse: Response: " + response.body().toString());
                ArrayList<HourlyWeather> hourlyWeathers = response.body().getHourly();
                setupHourlyRecyclerView(response, hourlyWeathers, recyclerView);
                setupHourlyChart(hourlyWeathers, lineChart);
            }
            @Override
            public void onFailure(Call<Hourly> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong" + t.getMessage());
            }
        });
    }

    public void fetchDailyData(RecyclerView recyclerView, LineChart lineChart, Location coordinates) {
        Retrofit retrofit = getRetrofit();
        OpenWeatherApi api = retrofit.create(OpenWeatherApi.class);
        Call<Daily> call = api.getDailyData(coordinates.getLatitude(), coordinates.getLongitude(), "metric");
        //noinspection NullableProblems
        call.enqueue(new Callback<Daily>() {
            @Override
            public void onResponse(Call<Daily> call, Response<Daily> response) {
                Log.d(TAG, "onResponse: Server Response: " + response.toString());
                assert response.body() != null;
                Log.d(TAG, "onResponse: Response" + response.body().toString());
                ArrayList<DailyWeather> dailyWeathers = response.body().getDaily();
                setupDailyRecyclerView(response, dailyWeathers, recyclerView);
                setupDailyChart(dailyWeathers, lineChart);
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

    private void setupHourlyChart(ArrayList<HourlyWeather> hourlyWeathers, LineChart lineChart) {
        List<Entry> temperature = new ArrayList<>();
        List<Entry> feelsLike = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Entry entry = new Entry(i, (float) (hourlyWeathers.get(i).getTemperature() * 1.00f));
            Entry entry1 = new Entry(i, (float) (hourlyWeathers.get(i).getFeelsLike() * 1.00f));
            temperature.add(entry);
            feelsLike.add(entry1);
        }
        LineDataSet set = new LineDataSet(temperature, "temperature");
        LineDataSet set1 = new LineDataSet(feelsLike, "feels like");
        styleLineDataSet(set, set1);
        List<ILineDataSet> dataSet = new ArrayList<>();
        dataSet.add(set);
        dataSet.add(set1);
        LineData data = new LineData(dataSet);
        lineChart.setData(data);
        styleLineChart(lineChart);
    }


    private void setupDailyChart(ArrayList<DailyWeather> dailyWeathers, LineChart lineChart) {
        List<Entry> maxTempValues = new ArrayList<>();
        List<Entry> minTempValues = new ArrayList<>();
        for (int i = 0; i < dailyWeathers.size(); i++) {
            Entry entry = new Entry(i, (float) (dailyWeathers.get(i).getTemperatures().getMax() * 1.00f));
            Entry entry1 = new Entry(i, (float) (dailyWeathers.get(i).getTemperatures().getMin() * 1.00f));
            maxTempValues.add(entry);
            minTempValues.add(entry1);
        }
        LineDataSet set1 = new LineDataSet(maxTempValues, "max");
        LineDataSet set2 = new LineDataSet(minTempValues, "min");
        styleLineDataSet(set1, set2);
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        LineData data = new LineData(dataSets);
        styleLineChart(lineChart);
        lineChart.setData(data);
    }

    private void styleLineDataSet(LineDataSet set1, LineDataSet set2) {
        set1.setDrawFilled(true);
        set1.setFillColor(Color.RED);
        set1.setColor(Color.RED);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set2.setDrawFilled(true);
        set2.setFillColor(Color.BLUE);
        set2.setColor(Color.BLUE);
        set2.setDrawCircles(false);
        set2.setDrawValues(false);
    }

    private void styleLineChart(LineChart lineChart) {
//        lineChart.invalidate();
        lineChart.getDescription().setPosition(0,0);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.animateY(1000);
    }
}
