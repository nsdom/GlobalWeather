package com.nsdom.globalweather.locations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.pojo.Location;
import com.nsdom.globalweather.locations.utils.LocationsContract;
import com.nsdom.globalweather.locations.utils.LocationsDBHelper;
import com.nsdom.globalweather.network.HereApi;
import com.nsdom.globalweather.viewmodel.ViewModel;

import kotlin.jvm.internal.PackageReference;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationsActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private AutoCompleteTextView autoCompleteTextView;
    private HereApi autoCompleteApi;
    private static final int MENU_ITEM = 2;
    private ViewModel viewModel;
    private Context context = LocationsActivity.this;
    private SQLiteDatabase mDatabase;
    private LocationsAdapter mAdapter;


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
        viewModel.searchLocation(autoCompleteApi, autoCompleteTextView);
    }

    private void setupWidgets() {

        LocationsDBHelper dbHelper = new LocationsDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        autoCompleteTextView = findViewById(R.id.auto_complete_txt_view);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LocationsAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void setupNetwork() {
        Retrofit autoCompleteRetro = new Retrofit.Builder()
                .baseUrl("https://autocomplete.geocoder.api.here.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       autoCompleteApi = autoCompleteRetro.create(HereApi.class);

    }

    private void setBottomNavigation() {
        viewModel.enableNavigation(bottomNavigationView);
        viewModel.setMenuItemChecked(bottomNavigationView, MENU_ITEM);
    }

    private Cursor getAllItems() {
        return mDatabase.query(
                LocationsContract.LocationEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                LocationsContract.LocationEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    private void removeItem(long id) {
        mDatabase.delete(LocationsContract.LocationEntry.TABLE_NAME,
                LocationsContract.LocationEntry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }
}
