package com.nsdom.globalweather.forecast;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.viewmodel.ViewModel;

public class ForecastActivity extends AppCompatActivity {

    private BottomNavigationView  bottomNavigationView;
    private Context context = ForecastActivity.this;
    private ViewModel viewModel;
    private static final int MENU_ITEM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        setupWidgets();
        setupViewModel();
        setBottomNavigation();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupViewModel() {
        viewModel = new ViewModel(context);

    }

    private void setupWidgets() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
    }

    private void setBottomNavigation() {
        viewModel.enableNavigation(bottomNavigationView);
        viewModel.setMenuItemChecked(bottomNavigationView, MENU_ITEM);
    }
}
