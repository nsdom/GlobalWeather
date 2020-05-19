package com.nsdom.globalweather.locations;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsdom.globalweather.R;
import com.nsdom.globalweather.forecast.ForecastActivity;
import com.nsdom.globalweather.locations.utils.LocationsContract;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    private final Context mContext;
    private Cursor mCursor;

    public LocationsAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public LocationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_locations_recycler_view_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsAdapter.ViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String address = mCursor.getString(mCursor.getColumnIndex(LocationsContract.LocationEntry.COLUMN_ADDRESS));
        Double latitude = mCursor.getDouble(mCursor.getColumnIndex(LocationsContract.LocationEntry.COLUMN_LATITUDE));
        Double longitude = mCursor.getDouble(mCursor.getColumnIndex(LocationsContract.LocationEntry.COLUMN_LONGITUDE));
        holder.addressTxtView.setText(address);
        long id = mCursor.getLong(mCursor.getColumnIndex(LocationsContract.LocationEntry._ID));
        holder.itemView.setTag(id);

        holder.addressTxtView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ForecastActivity.class);
            intent.putExtra("address", address);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            mContext.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor!= null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView addressTxtView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            addressTxtView = itemView.findViewById(R.id.address_txt_view);
        }
    }
}
