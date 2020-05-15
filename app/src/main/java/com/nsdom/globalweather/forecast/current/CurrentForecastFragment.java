package com.nsdom.globalweather.forecast.current;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.ForecastModel;

public class CurrentForecastFragment extends Fragment {

    private static final String TAG = "CurrentForecastFragment";
    private Context context = getActivity();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_forecast, container, false);

        ForecastModel model = new ForecastModel(context);
        model.fetchCurrentData(view);

        return view;
    }
}
