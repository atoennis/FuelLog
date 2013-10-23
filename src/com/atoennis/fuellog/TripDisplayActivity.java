package com.atoennis.fuellog;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.atoennis.fuellog.NotifyTripDialogFragment.NoticeDialogListener;
import com.atoennis.fuellog.domain.Trip;

public class TripDisplayActivity extends Activity implements NoticeDialogListener
{
    private static final String EXTRA_TRIP = "EXTRA_TRIP";

    private Trip                trip;

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

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            trip = (Trip) extras.getSerializable(EXTRA_TRIP);
        }

        if (savedInstanceState != null)
        {
            trip = (Trip) savedInstanceState.getSerializable(EXTRA_TRIP);
        }
        else
        {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.trip_display, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable(EXTRA_TRIP, trip);

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_discard:
                deleteTrip();
                return true;
            case R.id.action_edit:
                launchTripForm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteTrip()
    {
        NotifyTripDialogFragment fragment = NotifyTripDialogFragment.newInstance();
        fragment.show(getFragmentManager(), "DIALOG_FRAGMENT");
    }

    private void launchTripForm()
    {
        Intent intent = TripFormActivity.buildTripFormActivityIntent(this, trip);
        startActivity(intent);
    }

    @Override
    public void onDeleteTrip(DialogFragment dialog)
    {
        dialog.dismiss();

        String where = String.format("%s = %d", FuelTripContract.TripEntry._ID, trip.id);
        getContentResolver().delete(FuelTripContract.TripEntry.TRIP_CONTENT_URI, where, null);
    }

    @Override
    public void onCancel(DialogFragment dialog)
    {
        dialog.getDialog().cancel();
    }
}
