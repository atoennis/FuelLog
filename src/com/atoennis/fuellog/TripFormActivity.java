package com.atoennis.fuellog;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.atoennis.fuellog.DatePickerFragment.OnDatePickerInteractionListener;
import com.atoennis.fuellog.TripFormFragment.OnTripFormInteractionListener;
import com.atoennis.fuellog.domain.Trip;

public class TripFormActivity extends FragmentActivity
    implements OnTripFormInteractionListener, OnDatePickerInteractionListener
{

    private static final String EXTRA_TRIP      = "EXTRA_TRIP";
    private static final String DIALOG_FRAGMENT = "DIALOG_FRAGMENT";
    private TripFormFragment    tripFragment;
    private Trip                trip;

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
        // TODO: Editing is always inserting rather than updating.
        // TODO: Editing a date always shows today rather than the actual set date.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_form);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            trip = (Trip) extras.getSerializable(EXTRA_TRIP);
        }

        if (savedInstanceState == null)
        {
            FragmentManager manager = getSupportFragmentManager();
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
    public void onDateSelectorPressed()
    {
        DatePickerFragment fragment = DatePickerFragment.newInstance();
        fragment.show(getSupportFragmentManager(), DIALOG_FRAGMENT);
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

            getContentResolver().insert(FuelTripContract.TripEntry.TRIP_CONTENT_URI,
                trip.getContentValues());
        }
        else
        {
            Toast.makeText(this, "Empty trip not saved.", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
