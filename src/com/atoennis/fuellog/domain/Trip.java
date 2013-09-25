package com.atoennis.fuellog.domain;

import java.io.Serializable;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;

import com.atoennis.fuellog.FuelTripContract.TripEntry;

public class Trip implements Serializable
{
    private static final long serialVersionUID = 362676478003603606L;

    public final int          id;
    public final Date         date;
    public final int          distance;
    public final double       volume;
    public final double       volumePrice;

    public Trip(int id, Date date, int distance, double volume, double volumePrice)
    {
        this.id = id;
        this.date = date;
        this.distance = distance;
        this.volume = volume;
        this.volumePrice = volumePrice;
    }

    public ContentValues getContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(TripEntry.COLUMN_NAME_TRIP_DATE, date.getTime());
        values.put(TripEntry.COLUMN_NAME_TRIP_ODOMETER, distance);
        values.put(TripEntry.COLUMN_NAME_TRIP_VOLUME, volume);
        values.put(TripEntry.COLUMN_NAME_TRIP_VOLUME_PRICE, volumePrice);

        return values;
    }

    public static Trip fromCursor(Cursor cursor)
    {
        if (cursor != null)
        {
            int id = cursor.getInt(cursor.getColumnIndex(TripEntry._ID));
            long milliseconds = cursor.getLong(cursor
                .getColumnIndex(TripEntry.COLUMN_NAME_TRIP_DATE));
            String distance = cursor.getString(cursor
                .getColumnIndex(TripEntry.COLUMN_NAME_TRIP_ODOMETER));
            String volume = cursor.getString(cursor
                .getColumnIndex(TripEntry.COLUMN_NAME_TRIP_VOLUME));
            String volumePrice = cursor.getString(cursor
                .getColumnIndex(TripEntry.COLUMN_NAME_TRIP_VOLUME_PRICE));

            return new Trip(id, new Date(milliseconds), Integer.valueOf(distance).intValue(),
                Double.valueOf(volume).doubleValue(), Double.valueOf(volumePrice).doubleValue());
        }

        return new Trip(0, new Date(), 0, 0, 0);
    }
}
