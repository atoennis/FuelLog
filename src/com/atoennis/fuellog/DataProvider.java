package com.atoennis.fuellog;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DataProvider extends ContentProvider
{
    private static final String LOG_TAG = DataProvider.class.getSimpleName();

    public DataProvider()
    {
    }

    @Override
    public String getType(Uri uri)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate()
    {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
        String sortOrder)
    {
        SQLiteDatabase db = new FuelTripDbHelper(getContext()).getWritableDatabase();

        Cursor cursor = db.query(FuelTripContract.TripEntry.TABLE_NAME, projection, selection,
            selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        SQLiteDatabase db = new FuelTripDbHelper(getContext()).getWritableDatabase();

        long rowId = db.insert(FuelTripContract.TripEntry.TABLE_NAME, null, values);

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.withAppendedPath(uri, Long.toString(rowId));
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = new FuelTripDbHelper(getContext()).getWritableDatabase();

        getContext().getContentResolver().notifyChange(uri, null);
        return db.update(FuelTripContract.TripEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = new FuelTripDbHelper(getContext()).getWritableDatabase();

        int rows = db.delete(FuelTripContract.TripEntry.TABLE_NAME, selection, selectionArgs);

        Log.d(LOG_TAG, String.format("Deleting from uri %s", uri.toString()));
        getContext().getContentResolver().notifyChange(uri, null);

        return rows;
    }
}
