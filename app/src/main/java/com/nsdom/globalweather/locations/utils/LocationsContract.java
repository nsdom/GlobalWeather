package com.nsdom.globalweather.locations.utils;

import android.provider.BaseColumns;

public class LocationsContract {

    private LocationsContract() {}

    public static final class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "addressList";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
