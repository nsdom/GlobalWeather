package com.nsdom.globalweather.forecast.daily;

import android.content.Context;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.ForecastModel;

public class DailyForecastFragment extends Fragment {

    private static final String TAG = "DailyForecastFragment";
    private Context context = getActivity();
    private RecyclerView recyclerView;
    private LineChart lineChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_forecast, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        lineChart = view.findViewById(R.id.line_chart_view);
        ForecastModel model = new ForecastModel(context);
        model.fetchDailyData(recyclerView, lineChart);
        return view;
    }
}
