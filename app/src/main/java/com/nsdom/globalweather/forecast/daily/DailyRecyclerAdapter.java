package com.nsdom.globalweather.forecast.daily;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.pojo.DailyWeather;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

public class DailyRecyclerAdapter extends RecyclerView.Adapter<DailyRecyclerAdapter.MyViewHolder> {

    private static final String TAG = "DailyRecyclerAdapter";
    private final ArrayList<DailyWeather> dailyWeatherList;
    private final String timezone;

    public DailyRecyclerAdapter(ArrayList<DailyWeather> dailyWeatherList, String timezone) {
        this.dailyWeatherList = dailyWeatherList;
        this.timezone = timezone;
    }

    @NonNull
    @Override
    public DailyRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_daily_recycler_view_card, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DailyRecyclerAdapter.MyViewHolder holder, int position) {

        long maxTemp = Math.round(dailyWeatherList.get(position).getTemperatures().getMax());
        long minTemp = Math.round(dailyWeatherList.get(position).getTemperatures().getMin());
        double windSpeed = Math.round(dailyWeatherList.get(position).getWindSpeed());
        float windDegree = dailyWeatherList.get(position).getWindDegree();
        int clouds = dailyWeatherList.get(position).getClouds();
        int humidity = dailyWeatherList.get(position).getHumidity();
        String description = dailyWeatherList.get(position).getWeather().get(0).getDescription();
        String iconString = dailyWeatherList.get(position).getWeather().get(0).getIcon();
        String iconUrl = "https://openweathermap.org/img/wn/" + iconString + "@2x.png";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        String hour = sdf.format(dailyWeatherList.get(position).getDate() * 1000L);

        holder.dateTxtView.setText(hour);
        holder.maxTempTxtView.setText(Long.toString(maxTemp));
        holder.minTempTxtView.setText(Long.toString(minTemp));
        holder.windSpeedTxtView.setText(Double.toString(windSpeed));
        holder.windDirectionImgView.setRotation(windDegree);
        holder.cloudsTxtView.setText(Integer.toString(clouds));
        holder.humidityTxtView.setText(Integer.toString(humidity));
        holder.descriptionTxtView.setText(description);
        Picasso.get().load(iconUrl).into(holder.iconDescriptionImgView);
        Log.d(TAG, "onBindViewHolder: iconUrl: " + iconUrl);

    }

    @Override
    public int getItemCount() {
        return dailyWeatherList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTxtView, maxTempTxtView, minTempTxtView, windSpeedTxtView,
                cloudsTxtView, humidityTxtView, descriptionTxtView;
        private ImageView windDirectionImgView, iconDescriptionImgView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTxtView = itemView.findViewById(R.id.date_textView);
            maxTempTxtView = itemView.findViewById(R.id.temp_max_txt_view);
            minTempTxtView = itemView.findViewById(R.id.temp_min_txt_view);
            windSpeedTxtView = itemView.findViewById(R.id.wind_textView);
            cloudsTxtView = itemView.findViewById(R.id.clouds_textView);
            humidityTxtView = itemView.findViewById(R.id.humidity_textView);
            descriptionTxtView = itemView.findViewById(R.id.description_txt_view);
            windDirectionImgView = itemView.findViewById(R.id.wind_direction_img_view);
            iconDescriptionImgView = itemView.findViewById(R.id.iconDescription_img_view);
        }
    }
}
