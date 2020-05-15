package com.nsdom.globalweather.forecast.hourly;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.jjoe64.graphview.GraphView;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.ForecastModel;
import com.nsdom.globalweather.forecast.pojo.HourlyWeather;

import java.util.ArrayList;

public class HourlyForecastFragment extends Fragment {

    private static final String TAG = "HourlyForecastFragment";
    private Context context = getActivity();
    private RecyclerView recyclerView;
    private ArrayList<HourlyWeather> hourlyWeathers = new ArrayList<>();
    private ForecastModel model;
    private LineChart lineChart;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hourly_forecast, container, false);
        Log.d(TAG, "onCreateView: ");

        recyclerView = view.findViewById(R.id.recyclerView);
        model = new ForecastModel(context);
        lineChart = view.findViewById(R.id.line_chart_view);
        model.fetchHourlyData(recyclerView, lineChart);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        model.fetchHourlyData(recyclerView, lineChart);
    }

    @Override
    public void onPause() {
        super.onPause();
        lineChart.getData().clearValues();
        recyclerView.invalidate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lineChart.getData().clearValues();
        recyclerView.invalidate();
    }
}
