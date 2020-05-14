package com.nsdom.globalweather.forecast.hourly;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.ForecastActivity;
import com.nsdom.globalweather.forecast.hourly.pojo.Hourly;
import com.nsdom.globalweather.forecast.hourly.pojo.HourlyWeather;
import com.nsdom.globalweather.forecast.network.OpenWeatherApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HourlyForecastFragment extends Fragment {

    private static final String TAG = "HourlyForecastFragment";
    private Context context = getActivity();
    private RecyclerView recyclerView;
    private ArrayList<HourlyWeather> hourlyWeathers = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hourly_forecast, container, false);
        Log.d(TAG, "onCreateView: ");

        recyclerView = view.findViewById(R.id.recyclerView);
        GraphView graphView = view.findViewById(R.id.graphView);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherApi openWeatherApi = retrofit.create(OpenWeatherApi.class);
        Call<Hourly> call = openWeatherApi.getHourlyData(39.9163, -8.9520, "metric");
        call.enqueue(new Callback<Hourly>() {
            @Override
            public void onResponse(Call<Hourly> call, Response<Hourly> response) {

                Log.d(TAG, "onResponse: Server Response" + response.toString());
                assert response.body() != null;
                Log.d(TAG, "onResponse: Response: " + response.body().toString());
                hourlyWeathers = response.body().getHourly();
                String timezone = response.body().getTimezone();
                HourlyRecyclerAdapter adapter = new HourlyRecyclerAdapter(hourlyWeathers, timezone);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
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

            @Override
            public void onFailure(Call<Hourly> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong" + t.getMessage());
            }
        });





        return view;
    }
}
