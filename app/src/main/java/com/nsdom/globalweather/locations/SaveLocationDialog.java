package com.nsdom.globalweather.locations;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.ForecastActivity;
import com.nsdom.globalweather.forecast.pojo.Location;
import com.nsdom.globalweather.locations.utils.LocationsContract;
import com.nsdom.globalweather.locations.utils.LocationsDBHelper;
import com.nsdom.globalweather.viewmodel.ViewModel;

public class SaveLocationDialog extends AppCompatDialogFragment {

    private TextView addressTextView;
    private Location location;
    private SQLiteDatabase mDatabase;
    private LocationsAdapter mAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LocationsDBHelper dbHelper = new LocationsDBHelper(getActivity());
        mDatabase = dbHelper.getWritableDatabase();
        Bundle args = getArguments();
        assert args != null;
        String address = args.getString("address");
        Double latitude = args.getDouble("latitude");
        Double longitude = args.getDouble("longitude");
        location = new Location(latitude, longitude, address);

        View view = inflater.inflate(R.layout.dialog_save_location, null);
        builder.setView(view)
                .setNegativeButton("No", (dialog, which) -> {
                    navigateToForecastActivity();
                })
                .setNeutralButton("Cancel", ((dialog, which) -> {})
                )
                .setPositiveButton("ok", ((dialog, which) -> {
                            navigateToForecastActivity();
                            addLocation();
//                            mAdapter.swapCursor(getAllItems());
                        })
                );
        addressTextView = view.findViewById(R.id.address_txt_view);
        addressTextView.setText(location.getAddress());

        return builder.create();
    }

    private void navigateToForecastActivity() {
        Intent intent = new Intent(getActivity(), ForecastActivity.class);
        intent.putExtra("latitude", location.getLatitude());
        intent.putExtra("longitude", location.getLongitude());
        intent.putExtra("address", location.getAddress());
        getActivity().startActivity(intent);
    }

    private void addLocation() {
        String address = location.getAddress();
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();

        ContentValues cv = new ContentValues();
        cv.put(LocationsContract.LocationEntry.COLUMN_ADDRESS, address);
        cv.put(LocationsContract.LocationEntry.COLUMN_LATITUDE, latitude);
        cv.put(LocationsContract.LocationEntry.COLUMN_LONGITUDE, longitude);

        mDatabase.insert(LocationsContract.LocationEntry.TABLE_NAME, null, cv);
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



}
