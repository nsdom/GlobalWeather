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
        Log.d(TAG, "onCreateView: ");

        recyclerView = view.findViewById(R.id.recyclerView);
/*        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));
        hourlyWeatherList.add(new HourlyWeather("12:00h", "25ºC", "20mph"));*/
        HourlyRecyclerAdapter adapter = new HourlyRecyclerAdapter(hourlyWeatherList, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        GraphView graphView = view.findViewById(R.id.graphView);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, -10),
                new DataPoint(2, 6),
                new DataPoint(4, 9),
                new DataPoint(6, 12),
                new DataPoint(8, 15),
                new DataPoint(10,18),
                new DataPoint(12, 21),
                new DataPoint(14, 24),
                new DataPoint(16, 27),
                new DataPoint(18, 30),
                new DataPoint(20, 33),
                new DataPoint(22, 36)



        });
        series.setSpacing(10);
        final boolean[] negativeData = {false};
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {

                if (data.getY() >= 50) {
                    return Color.rgb(255, 0, 0);
                } else if (data.getY() < 50 && data.getY() >= 20) {
                    return Color.rgb(255, 255 - (255 * (int) data.getY()) / 50, 0);
                } else if (data.getY() < 0) {
                    return Color.rgb((255 * (int) Math.abs(data.getY())) / 20, 0, 255);
                } else {
                    return Color.rgb(0, (255 * (int) data.getY()) / 20, 255);
                }
            }
        });

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMaxX(25);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMaxY(45);
        graphView.getViewport().setMinX(-1);
        graphView.getViewport().setMinY(series.getLowestValueY());
        graphView.getGridLabelRenderer().setNumHorizontalLabels(6);
        graphView.getGridLabelRenderer().setNumVerticalLabels(10);
        graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphView.addSeries(series);
        graphView.setTitle("Temperature");;


        return view;
    }
}
