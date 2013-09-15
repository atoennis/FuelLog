package com.atoennis.fuellog;

import android.net.Uri;
import android.provider.BaseColumns;

public class FuelTripContract
{
    public static final String SCHEME      = "content";
    public static final String AUTHORITY   = "com.atoennis.fuellog.provider";
    public static final Uri    CONTENT_URI = Uri.parse(SCHEME
                                               + "://" + AUTHORITY);

    public FuelTripContract()
    {
    }

    public static abstract class TripEntry implements BaseColumns
    {
        public static final String TABLE_NAME                    = "trip";
        public static final String COLUMN_NAME_TRIP_DATE         = "tripdate";
        public static final String COLUMN_NAME_TRIP_ODOMETER     = "odometer";
        public static final String COLUMN_NAME_TRIP_VOLUME       = "volume";
        public static final String COLUMN_NAME_TRIP_VOLUME_PRICE = "volumeprice";

        public static final Uri    TRIP_CONTENT_URI              = Uri.withAppendedPath(
                                                                     CONTENT_URI, TABLE_NAME);
    }
}
