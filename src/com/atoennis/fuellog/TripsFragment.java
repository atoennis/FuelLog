package com.atoennis.fuellog;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
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
public class TripsFragment extends Fragment implements AbsListView.OnItemClickListener
{

    private OnTripsInteractionListener listener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView                listView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with Views.
     */
    private ListAdapter                adapter;

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

        // Set the adapter
        listView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) listView).setAdapter(adapter);

        // Set OnItemClickListener so we can be notified on item clicks
        listView.setOnItemClickListener(this);

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
        super.onDetach();
        listener = null;
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

    public void onCursorChanged(Cursor newCursor)
    {
        if (newCursor != null)
        {
            adapter = new TripsAdapter(getActivity(), newCursor, 0);

            listView.setAdapter(adapter);
        }
        else
        {
            listView.setAdapter(null);
        }
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

    private class TripsAdapter extends CursorAdapter
    {

        public TripsAdapter(Context context, Cursor c, int flags)
        {
            super(context, c, flags);
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
