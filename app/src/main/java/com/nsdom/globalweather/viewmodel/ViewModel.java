package com.nsdom.globalweather.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.ForecastActivity;
import com.nsdom.globalweather.locations.LocationsActivity;
import com.nsdom.globalweather.map.MapsActivity;
import com.nsdom.globalweather.news.NewsActivity;
import com.nsdom.globalweather.settings.SettingsActivity;

public class ViewModel {

    private final Context context;

    public ViewModel(Context context) {
        this.context = context;
    }

    public void enableNavigation(BottomNavigationView view) {
        view.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.forecast:
                    Intent intent1 = new Intent(context, ForecastActivity.class);
                    context.startActivity(intent1);
                    break;
                case R.id.locations:
                    Intent intent2 = new Intent(context, LocationsActivity.class);
                    context.startActivity(intent2);
                    break;
                case R.id.map:
                    Intent intent3 = new Intent(context, MapsActivity.class);
                    context.startActivity(intent3);
                    break;
                case R.id.news:
                    Intent intent4 = new Intent(context, NewsActivity.class);
                    context.startActivity(intent4);
                    break;
                case R.id.settings:
                    Intent intent5 = new Intent(context, SettingsActivity.class);
                    context.startActivity(intent5);
            }
            return false;
        });
    }

    public void setMenuItemChecked(BottomNavigationView bottomNavigationView, int MENU_ITEM) {
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(MENU_ITEM);
        menuItem.setChecked(true);
    }
}
