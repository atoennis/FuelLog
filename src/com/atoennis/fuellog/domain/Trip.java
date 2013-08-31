package com.atoennis.fuellog.domain;

import java.util.Date;

import android.content.ContentValues;

import com.atoennis.fuellog.FuelTripContract.TripEntry;

public class Trip
{
    public final Date   date;
    public final int    distance;
    public final int    volume;
    public final double volumePrice;

    public Trip(Date date, int distance, int volume, double volumePrice)
    {
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
}
