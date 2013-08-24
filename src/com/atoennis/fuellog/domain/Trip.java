package com.atoennis.fuellog.domain;

import java.util.Date;

public class Trip
{
    public final Date date;
    public final int  distance;
    public final int  volume;
    public final int  volumePrice;

    public Trip(Date date, int distance, int volume, int volumePrice)
    {
        this.date = date;
        this.distance = distance;
        this.volume = volume;
        this.volumePrice = volumePrice;
    }
}
