package com.atoennis.fuellog;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

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

        if (emptyText instanceof TextView)
        {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public void onCursorChanged(Cursor newCursor)
    {
        String[] fromColumns = {
            FuelTripContract.TripEntry.COLUMN_NAME_TRIP_DATE,
            FuelTripContract.TripEntry.COLUMN_NAME_TRIP_ODOMETER,
            FuelTripContract.TripEntry.COLUMN_NAME_TRIP_VOLUME,
            FuelTripContract.TripEntry.COLUMN_NAME_TRIP_VOLUME_PRICE};
        int[] toFields = {
            R.id.date_display, R.id.odometer_display, R.id.volume_display,
            R.id.volume_price_display};

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.fuel_trip_item, newCursor,
            fromColumns, toFields, 0);

        listView.setAdapter(adapter);
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
    }

}
