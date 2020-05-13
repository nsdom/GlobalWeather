package com.nsdom.globalweather.forecast.hourly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.hourly.pojo.HourlyWeather;

import java.util.List;

public class HourlyRecyclerAdapter extends RecyclerView.Adapter<HourlyRecyclerAdapter.HourlyViewHolder> {

    private final List<HourlyWeather> hourlyWeatherList;
    private final Context context;

    public HourlyRecyclerAdapter(List<HourlyWeather> hourlyWeatherList, Context context) {
        this.hourlyWeatherList = hourlyWeatherList;
        this.context = context;
    }

    @NonNull
    @Override
    public HourlyRecyclerAdapter.HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_hourly_recycler_view_card, parent, false);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyRecyclerAdapter.HourlyViewHolder holder, int position) {

        holder.hourTxtView.setText(hourlyWeatherList.get(position).getHour());
        holder.temperatureTxtView.setText(hourlyWeatherList.get(position).getTemperature());
        holder.windTxtView.setText(hourlyWeatherList.get(position).getWind());
    }

    @Override
    public int getItemCount() {
        return hourlyWeatherList.size();
    }

    public class HourlyViewHolder extends RecyclerView.ViewHolder {
        private TextView hourTxtView, temperatureTxtView, windTxtView;
        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            hourTxtView = itemView.findViewById(R.id.hour_textView);
            temperatureTxtView = itemView.findViewById(R.id.temperature_textView);
            windTxtView = itemView.findViewById(R.id.wind_textView);

        }
    }
}
