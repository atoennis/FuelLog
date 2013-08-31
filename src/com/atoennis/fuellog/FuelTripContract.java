package com.atoennis.fuellog;

import android.provider.BaseColumns;

public class FuelTripContract
{
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
    }
}
