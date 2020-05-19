package com.nsdom.globalweather.forecast.current;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.ForecastModel;
import com.nsdom.globalweather.forecast.pojo.Location;

public class CurrentForecastFragment extends Fragment {

    private static final String TAG = "CurrentForecastFragment";
    private Context context = getActivity();
    private Double latitude;
    private Double longitude;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_forecast, container, false);

        ForecastModel model = new ForecastModel(context);


        Intent intent = getActivity().getIntent();
        latitude = intent.getDoubleExtra("latitude", 39.6521);
        longitude = intent.getDoubleExtra("longitude", -7.6722);
        Location coordinate = new Location(latitude, longitude, "");

        Log.d(TAG, "onCreate: Coordinates" + latitude + ", " + longitude);
        model.fetchCurrentData(view, coordinate);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
