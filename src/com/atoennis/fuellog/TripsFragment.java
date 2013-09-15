package com.atoennis.fuellog;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.atoennis.fuellog.domain.Trip;
import com.atoennis.fuellog.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks} interface.
 */
public class TripsFragment extends Fragment
    implements AbsListView.OnItemClickListener, LoaderCallbacks<Cursor>
{

    private static final int           TRIPS_LOAD = 0;

    private OnTripsInteractionListener listener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView                listView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with Views.
     */
    private TripsAdapter               adapter;

    public static TripsFragment newInstance()
    {
        TripsFragment fragment = new TripsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
     * screen orientation changes).
     */
    public TripsFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_trip, container, false);

        adapter = new TripsAdapter(getActivity());
        listView = (AbsListView) view.findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        // Set OnItemClickListener so we can be notified on item clicks
        listView.setOnItemClickListener(this);

        getLoaderManager().initLoader(TRIPS_LOAD, null, this);

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            listener = (OnTripsInteractionListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(String.format("%s must implement %s", activity.toString(),
                OnTripsInteractionListener.class.getSimpleName()));
        }
    }

    @Override
    public void onDetach()
    {
        listener = null;
        getLoaderManager().destroyLoader(TRIPS_LOAD);
        if (adapter != null)
        {
            adapter.changeCursor(null);
            adapter = null;
        }
        super.onDetach();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (null != listener)
        {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            listener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when the list is empty. If
     * you would like to change the text, call this method to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText)
    {
        View emptyView = listView.getEmptyView();

        if (emptyView instanceof TextView)
        {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle)
    {
        Uri uri = FuelTripContract.TripEntry.TRIP_CONTENT_URI;
        String[] projection = null;

        switch (id)
        {
            case TRIPS_LOAD:
                return new CursorLoader(getActivity(), uri, projection, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        adapter.swapCursor(null);
    }
    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other
     * fragments contained in that activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html" >Communicating
     * with Other Fragments</a> for more information.
     */
    public interface OnTripsInteractionListener
    {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);

        public void onDeleteTripPressed(Trip trip);
    }

    public class TripsAdapter extends CursorAdapter
    {
        public TripsAdapter(Context context)
        {
            super(context, null, false);
        }

        @Override
        public void bindView(View view, Context context, final Cursor cursor)
        {
            Trip trip = Trip.fromCursor(cursor);
            TextView date = (TextView) view.findViewById(R.id.date_display);
            TextView distance = (TextView) view.findViewById(R.id.odometer_display);
            TextView volume = (TextView) view.findViewById(R.id.volume_display);
            TextView volumePrice = (TextView) view.findViewById(R.id.volume_price_display);
            Button delete = (Button) view.findViewById(R.id.delete);

            date.setText(new SimpleDateFormat("EE, MMM d, yyyy").format(trip.date));
            distance.setText(Integer.toString(trip.distance));
            volume.setText(Integer.toString(trip.volume));
            volumePrice.setText(Double.toString(trip.volumePrice));

            delete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        listener.onDeleteTripPressed(Trip.fromCursor(cursor));
                    }
                }
            });

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent)
        {
            LayoutInflater inflator = LayoutInflater.from(context);
            View view = inflator.inflate(R.layout.fuel_trip_item, null);

            return view;
        }

    }
}
