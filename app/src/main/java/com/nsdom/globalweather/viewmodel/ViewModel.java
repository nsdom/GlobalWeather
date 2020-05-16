package com.nsdom.globalweather.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListPopupWindow;

import androidx.annotation.RequiresApi;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.ForecastActivity;
import com.nsdom.globalweather.locations.LocationsActivity;
import com.nsdom.globalweather.locations.pojo.HereSearchResponse;
import com.nsdom.globalweather.locations.pojo.Locations;
import com.nsdom.globalweather.locations.utils.ArrayAdapterNoSpaceFilter;
import com.nsdom.globalweather.map.MapsActivity;
import com.nsdom.globalweather.network.HereApi;
import com.nsdom.globalweather.news.NewsActivity;
import com.nsdom.globalweather.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;

public class ViewModel {

    private static final String TAG = "ViewModel";
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


    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    public void searchLocation(Button searchButton, HereApi api, AutoCompleteTextView autoCompleteTextView) {

        Observable<String> searchTxtInput = RxTextView.textChanges(autoCompleteTextView)
                .map(CharSequence::toString);

        searchTxtInput.filter(searchTxt -> searchTxt.length() > 3)
                .doOnNext(searchText -> Log.d(TAG, "searchLocation: " + searchText.replace(" ", "%20")))
                .flatMap(searchText -> api.getSuggestLocations(searchText)
                        .subscribeOn(Schedulers.io()))
                .doOnNext(hereSearchResponse -> Log.d(TAG, "searchLocation: " + hereSearchResponse.toString()))
                .map(HereSearchResponse::getLocations)
                .doOnNext(locations -> Log.d(TAG, "searchLocation: " + locations))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(locations -> {
                            List<String> addressList = new ArrayList<>();
                            for (int i = 0; i < locations.size(); i++) {
                                addressList.add(locations.get(i).getLabel());
                            }
                            Log.d(TAG, "searchLocation: " + addressList);
                            ArrayAdapterNoSpaceFilter adapterNoSpaceFilter = new ArrayAdapterNoSpaceFilter<>(context, R.layout.support_simple_spinner_dropdown_item, addressList);
                            autoCompleteTextView.setAdapter(adapterNoSpaceFilter);
                            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Log.d(TAG, "onItemClick: " + addressList.get(position));
                                }
                            });
                            },
                        e -> Log.e(TAG, "Error getting auto complete locations ", e));

        Observable<Unit> buttonClickObservable = RxView.clicks(searchButton);

    }

}
