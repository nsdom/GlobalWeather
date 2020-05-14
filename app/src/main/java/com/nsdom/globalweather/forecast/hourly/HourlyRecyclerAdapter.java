package com.nsdom.globalweather.forecast.hourly;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.ForecastActivity;
import com.nsdom.globalweather.forecast.hourly.pojo.HourlyWeather;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class HourlyRecyclerAdapter extends RecyclerView.Adapter<HourlyRecyclerAdapter.HourlyViewHolder> {

    private static final String TAG = "HourlyRecyclerAdapter";
    private final ArrayList<HourlyWeather> hourlyWeatherList;
    private final String timezone;


    public HourlyRecyclerAdapter(ArrayList<HourlyWeather> hourlyWeatherList, String timezone) {
        this.hourlyWeatherList = hourlyWeatherList;
        this.timezone = timezone;
    }

    @NonNull
    @Override
    public HourlyRecyclerAdapter.HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_hourly_recycler_view_card, parent, false);
        return new HourlyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HourlyRecyclerAdapter.HourlyViewHolder holder, int position) {

        long temperature = Math.round(hourlyWeatherList.get(position).getTemperature());
        long feelsLike = Math.round(hourlyWeatherList.get(position).getFeelsLike());
        double windSpeed = Math.round(hourlyWeatherList.get(position).getWindSpeed());
        float windDegree = hourlyWeatherList.get(position).getWindDegree();
        int clouds = hourlyWeatherList.get(position).getClouds();
        int humidity = hourlyWeatherList.get(position).getHumidity();
        String description = hourlyWeatherList.get(position).getWeather().get(0).getDescription();
        String iconString = hourlyWeatherList.get(position).getWeather().get(0).getIcon();
        String iconUrl = "https://openweathermap.org/img/wn/" + iconString + "@2x.png";
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        String hour = sdf.format(hourlyWeatherList.get(position).getHour() * 1000L);

        holder.hourTxtView.setText(hour);
        holder.temperatureTxtView.setText(Long.toString(temperature));
        holder.feelsLikeTxtView.setText(Long.toString(feelsLike));
        holder.windTxtView.setText(Double.toString(windSpeed));
        holder.windDirectionImgView.setRotation(windDegree);
        holder.cloudsTxtView.setText(Integer.toString(clouds));
        holder.humidityTxtView.setText(Integer.toString(humidity));
        holder.descriptionTxtView.setText(description);
        Picasso.get().load(iconUrl).into(holder.iconDescriptionImgView);
        Log.d(TAG, "onBindViewHolder: iconUrl: " + iconUrl);

    }

    @Override
    public int getItemCount() {
        return 24;
    }

    public class HourlyViewHolder extends RecyclerView.ViewHolder {
        private TextView hourTxtView, temperatureTxtView, feelsLikeTxtView, windTxtView,
                cloudsTxtView, humidityTxtView, descriptionTxtView;

        private ImageView windDirectionImgView, iconDescriptionImgView;

        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            hourTxtView = itemView.findViewById(R.id.hour_textView);
            temperatureTxtView = itemView.findViewById(R.id.temperature_textView);
            feelsLikeTxtView = itemView.findViewById(R.id.feels_like_textView);
            windTxtView = itemView.findViewById(R.id.wind_textView);
            cloudsTxtView = itemView.findViewById(R.id.clouds_textView);
            humidityTxtView = itemView.findViewById(R.id.humidity_textView);
            descriptionTxtView = itemView.findViewById(R.id.description_txt_view);
            windDirectionImgView = itemView.findViewById(R.id.wind_direction_img_view);
            iconDescriptionImgView = itemView.findViewById(R.id.iconDescription_img_view);


        }
    }
}
