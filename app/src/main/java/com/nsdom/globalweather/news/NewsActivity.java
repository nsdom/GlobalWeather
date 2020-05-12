package com.nsdom.globalweather.news;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.viewmodel.ViewModel;

public class NewsActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private static final int MENU_ITEM = 3;
    private ViewModel viewModel;
    private Context context = NewsActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        setupWidgets();
        setupViewModel();
        setBottomNavigation();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
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
