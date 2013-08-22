package com.atoennis.fuellog;


import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Use the
 * {@link DatePickerFragment#newInstance} factory method to create an instance of this fragment.
 * 
 */
public class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener
{

    private OnDatePickerInteractionListener listener;

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     * 
     * @return A new instance of fragment DatePickerFragment.
     */
    public static DatePickerFragment newInstance()
    {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DatePickerFragment()
    {
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            listener = (OnDatePickerInteractionListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(String.format("%s must implement %s", activity.toString(),
                OnDatePickerInteractionListener.class.getSimpleName()));
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        listener = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        if (listener != null)
        {
            listener.onDateSelected(year, monthOfYear, dayOfMonth);
        }
    }

    public interface OnDatePickerInteractionListener
    {
        public void onDateSelected(int year, int monthOfYear, int dayOfMonth);
    }
}
