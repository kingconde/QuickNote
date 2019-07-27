package com.culturageek.quicknote.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.culturageek.quicknote.R;
import com.culturageek.quicknote.ui.interfaces.INoteListener;

public class CustomSimpleAlertDialog extends DialogFragment {
    public static final String TAG = "CustomSimpleAlertDialog";
    public static final String BUNDLE_DIALOG = "BUNDLE_DIALOG";
    private INoteListener.IDialogListener mIDialogListener;
    private String mMessage;
    private Bundle mBundle;

    public CustomSimpleAlertDialog() {
    }

    public static CustomSimpleAlertDialog getInstance(String mMessage){
        CustomSimpleAlertDialog dialog = new CustomSimpleAlertDialog();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_DIALOG, mMessage);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setDialogListener(INoteListener.IDialogListener mIDialogListener){
        this.mIDialogListener = mIDialogListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        loadBundle();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mMessage)
                .setPositiveButton(R.string.btn_positive_dialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User accepts the dialog.
                        mIDialogListener.actionDialog(id);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.btn_negative_dialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog.
                        mIDialogListener.actionDialog(id);
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void loadBundle() {
        if(getArguments() != null){
            mBundle = getArguments();
            mMessage = mBundle.getString(BUNDLE_DIALOG);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mIDialogListener = null;
    }
}
