package com.atoennis.fuellog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atoennis.fuellog.FuelTripContract.TripEntry;

public class FuelTripDbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME    = "FuelTrip.db";
    private static final int    DATABASE_VERSION = 1;

    private static final String TEXT_TYPE        = " TEXT";
    private static final String INTEGER_TYPE     = " INTEGER";
    private static final String COMMA_SEP        = ",";
    private static final String SQL_CREATE_TRIP  = "CREATE TABLE "
                                                     + TripEntry.TABLE_NAME + " (" + TripEntry._ID
                                                     + " INTEGER PRIMARY KEY,"
                                                     + TripEntry.COLUMN_NAME_TRIP_DATE
                                                     + INTEGER_TYPE + COMMA_SEP
                                                     + TripEntry.COLUMN_NAME_TRIP_ODOMETER
                                                     + TEXT_TYPE + COMMA_SEP
                                                     + TripEntry.COLUMN_NAME_TRIP_VOLUME
                                                     + TEXT_TYPE + COMMA_SEP
                                                     + TripEntry.COLUMN_NAME_TRIP_VOLUME_PRICE
                                                     + TEXT_TYPE + " )";
    private static final String SQL_DELETE_TRIP  = "DROP TABLE IF EXISTS "
                                                     + TripEntry.TABLE_NAME;

    public FuelTripDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_TRIP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_TRIP);
        onCreate(db);
    }

}
