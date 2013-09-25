package com.atoennis.fuellog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.atoennis.fuellog.domain.Trip;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that contain this fragment
 * must implement the {@link TripFormFragment.OnTripFormInteractionListener} interface to handle
 * interaction events. Use the {@link TripFormFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class TripFormFragment extends Fragment
{
    private OnTripFormInteractionListener listener;
    private Button                        datePicker;
    private EditText                      distanceInput;
    private EditText                      volumeInput;
    private EditText                      volumePriceInput;

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     * 
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TripFormFragment.
     */
    public static TripFormFragment newInstance()
    {
        TripFormFragment fragment = new TripFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TripFormFragment()
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
        View view = inflater.inflate(R.layout.fragment_trip_form, container, false);

        datePicker = (Button) view.findViewById(R.id.date_picker);
        datePicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                {
                    listener.onDateSelectorPressed();
                }
            }
        });

        Calendar cal = Calendar.getInstance();
        setDateLabel(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH));

        distanceInput = (EditText) view.findViewById(R.id.distance);
        volumeInput = (EditText) view.findViewById(R.id.volume);
        volumePriceInput = (EditText) view.findViewById(R.id.volume_price);

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            listener = (OnTripFormInteractionListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(String.format("%s must implement %s", activity.toString(),
                OnTripFormInteractionListener.class.getSimpleName()));
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    public void onDateSelected(int year, int monthOfYear, int dayOfMonth)
    {
        setDateLabel(year, monthOfYear, dayOfMonth);
    }

    public boolean isFormValid()
    {
        String distance = distanceInput.getText().toString();
        String volume = volumeInput.getText().toString();
        String volumePrice = volumePriceInput.getText().toString();

        return distance != null
            && !distance.isEmpty() && volume != null && !volume.isEmpty() && volumePrice != null
            && !volumePrice.isEmpty();
    }

    public Trip getFormData()
    {
        String dateStr = datePicker.getText().toString();
        Date date = null;
        try
        {
            date = new SimpleDateFormat("EE, MMM d, yyyy").parse(dateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        int distance = Integer.parseInt(distanceInput.getText().toString());
        double volume = Double.parseDouble(volumeInput.getText().toString());
        double volumePrice = Double.parseDouble(volumePriceInput.getText().toString());

        return new Trip(0, date, distance, volume, volumePrice);
    }

    private void setDateLabel(int year, int monthOfYear, int dayOfMonth)
    {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, monthOfYear, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EE, MMM d, yyyy");
        String formattedDate = dateFormat.format(new Date(cal.getTimeInMillis()));

        datePicker.setText(formattedDate);
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
    public interface OnTripFormInteractionListener
    {
        public void onDateSelectorPressed();
    }
}
