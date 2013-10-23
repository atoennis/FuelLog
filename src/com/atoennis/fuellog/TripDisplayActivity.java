package com.atoennis.fuellog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.atoennis.fuellog.domain.Trip;

public class TripDisplayActivity extends Activity
{
    private static final String EXTRA_TRIP = "EXTRA_TRIP";

    public static Intent buildTripDisplayActivityIntent(Context context, Trip trip)
    {
        Intent intent = new Intent(context, TripDisplayActivity.class);

        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_TRIP, trip);
        intent.putExtras(extras);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_display);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trip_display, menu);
        return true;
    }

}
