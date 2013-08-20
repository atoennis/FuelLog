package com.atoennis.fuellog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.atoennis.fuellog.TripFormFragment.OnTripFormInteractionListener;

public class TripFormActivity extends FragmentActivity implements OnTripFormInteractionListener
{

    public static Intent buildTripFormActivityIntent(Context context)
    {
        Intent intent = new Intent(context, TripFormActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_form);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        TripFormFragment fragment = TripFormFragment.newInstance();
        transaction.add(R.id.fragment_container, fragment, "PRIMARY_FRAGMENT");
        transaction.commit();

        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar()
    {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.trip_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
