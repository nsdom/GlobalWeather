package com.nsdom.globalweather.map;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.viewmodel.ViewModel;

public class MapsActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private static final int MENU_ITEM = 1;
    private ViewModel viewModel;
    private Context context = MapsActivity.this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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
