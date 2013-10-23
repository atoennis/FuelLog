package com.atoennis.fuellog;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atoennis.fuellog.domain.Trip;


/**
 * A simple {@link android.app.Fragment} subclass. Activities that contain this fragment must
 * implement the {@link TripDisplayFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link TripDisplayFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class TripDisplayFragment extends Fragment
{
    private static final String              ARG_TRIP = "ARG_TRIP";

    private Trip                             trip;
    private OnTripDisplayInteractionListener listener;

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     * 
     * @param trip Trip to display in a read only state.
     * @return A new instance of fragment TripDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TripDisplayFragment newInstance(Trip trip)
    {
        TripDisplayFragment fragment = new TripDisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIP, trip);
        fragment.setArguments(args);
        return fragment;
    }

    public TripDisplayFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            trip = (Trip) getArguments().getSerializable(ARG_TRIP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_display, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (listener != null)
        {
            listener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            listener = (OnTripDisplayInteractionListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(String.format(
                "%s must implement OnTripDisplayInteractionListener", activity.getClass()
                    .getSimpleName()));
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
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
    public interface OnTripDisplayInteractionListener
    {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
