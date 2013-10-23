package com.atoennis.fuellog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class NotifyTripDialogFragment extends DialogFragment
{
    public interface NoticeDialogListener
    {
        public void onDeleteTrip(DialogFragment dialog);

        public void onCancel(DialogFragment dialog);
    }

    public static NotifyTripDialogFragment newInstance()
    {
        return new NotifyTripDialogFragment();
    }

    private NoticeDialogListener listener;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            listener = (NoticeDialogListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(String.format("%s must implement NoticeDialogListener",
                activity.getClass().getSimpleName()));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete_trip)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    listener.onDeleteTrip(NotifyTripDialogFragment.this);
                }
            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    listener.onCancel(NotifyTripDialogFragment.this);
                }
            });
        return builder.create();
    }
}