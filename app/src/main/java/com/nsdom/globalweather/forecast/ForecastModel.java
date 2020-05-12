package com.nsdom.globalweather.forecast;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.current.CurrentForecastFragment;
import com.nsdom.globalweather.forecast.daily.DailyForecastFragment;
import com.nsdom.globalweather.forecast.hourly.HourlyForecastFragment;

public class ForecastModel {

    private static final int BEHAVIOR = 1;
    private final Context context;

    public ForecastModel(Context context) {
        this.context = context;
    }

    public void setupViewPager(ViewPager viewPager, TabLayout tabLayout) {
        PagerAdapter pagerAdapter = new PagerAdapter(((ForecastActivity) context).getSupportFragmentManager(), BEHAVIOR);
        pagerAdapter.addFragment(new CurrentForecastFragment());
        pagerAdapter.addFragment(new HourlyForecastFragment());
        pagerAdapter.addFragment(new DailyForecastFragment());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_current);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_hourly);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_daily);
    }
}
