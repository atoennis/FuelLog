package com.atoennis.fuellog;

import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.atoennis.fuellog.DatePickerFragment.OnDatePickerInteractionListener;
import com.atoennis.fuellog.TripFormFragment.OnTripFormInteractionListener;
import com.atoennis.fuellog.domain.Trip;

public class TripFormActivity extends Activity
    implements OnTripFormInteractionListener, OnDatePickerInteractionListener
{

    private static final String EXTRA_TRIP      = "EXTRA_TRIP";
    private static final String DIALOG_FRAGMENT = "DIALOG_FRAGMENT";
    private TripFormFragment    tripFragment;
    private Trip                trip;
    private boolean             editState       = false;

    public static Intent buildTripFormActivityIntent(Context context)
    {
        Intent intent = new Intent(context, TripFormActivity.class);

        return intent;
    }

    public static Intent buildTripFormActivityIntent(Context context, Trip trip)
    {
        Intent intent = new Intent(context, TripFormActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_TRIP, trip);
        intent.putExtras(bundle);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_form);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            trip = (Trip) extras.getSerializable(EXTRA_TRIP);
            editState = true;
        }

        if (savedInstanceState == null)
        {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            TripFormFragment fragment = TripFormFragment.newInstance(trip);
            transaction.add(R.id.fragment_container, fragment, "PRIMARY_FRAGMENT");
            transaction.commit();
        }

        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar()
    {
        final LayoutInflater inflator = (LayoutInflater) getActionBar().getThemedContext()
            .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customActionBarView = inflator.inflate(R.layout.actionbar_custom_cancel_done,
            null);
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    onSavePressed();
                }
            });
        customActionBarView.findViewById(R.id.actionbar_cancel).setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    finish();
                }
            });

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM
            | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView, new ActionBar.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onAttachFragment(Fragment fragment)
    {
        if (fragment instanceof TripFormFragment)
        {
            tripFragment = (TripFormFragment) fragment;
        }
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onDestroy()
    {
        tripFragment = null;
        super.onDestroy();
    }


    @Override
    public void onDateSelectorPressed(Date date)
    {
        DatePickerFragment fragment = DatePickerFragment.newInstance(date);
        fragment.show(getFragmentManager(), DIALOG_FRAGMENT);
    }

    @Override
    public void onDateSelected(int year, int monthOfYear, int dayOfMonth)
    {
        if (tripFragment != null)
        {
            tripFragment.onDateSelected(year, monthOfYear, dayOfMonth);
        }
    }

    private void onSavePressed()
    {
        if (tripFragment != null
            && tripFragment.isFormValid())
        {
            Trip trip = tripFragment.getFormData();

            if (editState)
            {
                getContentResolver().update(FuelTripContract.TripEntry.TRIP_CONTENT_URI,
                    trip.getContentValues(), "_ID = "
                        + trip.id, null);
            }
            else
            {
                getContentResolver().insert(FuelTripContract.TripEntry.TRIP_CONTENT_URI,
                    trip.getContentValues());
            }
        }
        else
        {
            Toast.makeText(this, "Incomplete trip not saved.", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
