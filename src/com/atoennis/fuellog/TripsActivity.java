package com.atoennis.fuellog;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.atoennis.fuellog.TripsFragment.OnTripsInteractionListener;
import com.atoennis.fuellog.domain.Trip;

public class TripsActivity extends Activity
    implements LoaderCallbacks<Cursor>, OnTripsInteractionListener
{
    private static final String   EXTRA_TRIP = "EXTRA_TRIP";
    private static final int      TRIPS_LOAD = 0;
    private String[]              navigationItems;
    private DrawerLayout          drawerLayout;
    private ListView              drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence          title;
    private CharSequence          drawerTitle;
    private TripsFragment         tripsFragment;

    private class DrawerItemClickListener implements ListView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id)
        {
            // TODO: Do something here.
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        android.app.FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        tripsFragment = TripsFragment.newInstance();
        transaction.add(R.id.fragment_container, tripsFragment, "PRIMARY_FRAGMENT");
        transaction.commit();

        title = drawerTitle = getTitle();
        navigationItems = getResources().getStringArray(R.array.navigation_items);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item,
            navigationItems));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
        drawerLayout, /* DrawerLayout object */
        R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
        R.string.drawer_open, /* "open drawer" description for accessibility */
        R.string.drawer_close /* "close drawer" description for accessibility */
        )
        {
            public void onDrawerClosed(View view)
            {
                getActionBar().setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView)
            {
                getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getLoaderManager().initLoader(TRIPS_LOAD, null, this);
    }

    @Override
    public void setTitle(CharSequence title)
    {
        this.title = title;
        super.setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trips, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        switch (item.getItemId())
        {
            case R.id.menu_add_trip:
                onAddTripPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onAddTripPressed()
    {
        Intent intent = TripFormActivity.buildTripFormActivityIntent(this);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(String id)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDeleteTripPressed(Trip trip)
    {
        String where = String.format("%s = %d", FuelTripContract.TripEntry._ID, trip.id);
        getContentResolver().delete(FuelTripContract.TripEntry.CONTENT_URI, where, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle)
    {
        Uri uri = FuelTripContract.TripEntry.CONTENT_URI;
        String[] projection = null;

        switch (id)
        {
            case TRIPS_LOAD:
                return new CursorLoader(this, uri, projection, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor)
    {
        if (tripsFragment != null)
        {
            tripsFragment.onCursorChanged(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0)
    {

    }
}
