package com.nsdom.globalweather.locations;

import android.content.Context;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.network.HereApi;
import com.nsdom.globalweather.viewmodel.ViewModel;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationsActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private AutoCompleteTextView autoCompleteTextView;
    private Button button;
    HereApi api;
    private static final int MENU_ITEM = 2;
    private ViewModel viewModel;
    private Context context = LocationsActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        setupWidgets();
        setupNetwork();
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
        viewModel.searchLocation(button, api, autoCompleteTextView);
    }

    private void setupWidgets() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        autoCompleteTextView = findViewById(R.id.auto_complete_txt_view);
        button = findViewById(R.id.button);
    }

    private void setupNetwork() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://autocomplete.geocoder.api.here.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       api = retrofit.create(HereApi.class);
    }

    private void setBottomNavigation() {
        viewModel.enableNavigation(bottomNavigationView);
        viewModel.setMenuItemChecked(bottomNavigationView, MENU_ITEM);
    }
}
