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
import com.nsdom.globalweather.forecast.ForecastModel;
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
        ForecastModel model = new ForecastModel(context);
        GraphView graphView = view.findViewById(R.id.graphView);
        model.fetchData(recyclerView, graphView);

        return view;
    }
}
