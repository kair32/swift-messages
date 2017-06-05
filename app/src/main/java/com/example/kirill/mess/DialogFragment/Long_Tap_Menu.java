package com.example.kirill.mess.DialogFragment;

import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kirill.mess.Constants.Constants;
import com.example.kirill.mess.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.kirill.mess.R.id.long_tap_copy_button;
import static com.example.kirill.mess.R.id.long_tap_dell_button;

/**
 * Created by Kirill on 09.01.2017.
 */

public class Long_Tap_Menu extends DialogFragment implements View.OnClickListener{
    private View vRootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vRootView = inflater.inflate(R.layout.dialog_fragment_long_tap_menu, container, false);
        Button vDel = (Button) vRootView.findViewById(long_tap_dell_button);
        vDel.setOnClickListener(this);
        Button vCop = (Button) vRootView.findViewById(long_tap_copy_button);
        vCop.setOnClickListener(this);
        return vRootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case long_tap_dell_button:
                DatabaseReference mSimpleFireMessageDatabaseReference = FirebaseDatabase.getInstance().getReference();
                mSimpleFireMessageDatabaseReference.child(Constants.mDellDialog).removeValue();
                mSimpleFireMessageDatabaseReference = FirebaseDatabase.getInstance().getReference();
                mSimpleFireMessageDatabaseReference.child(Constants.UserEmail).child(Constants.mDellDialog).removeValue();
                dismiss();
                break;
            case long_tap_copy_button:
                ClipboardManager clipboard = (ClipboardManager) vRootView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", Constants.mDellDialog);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(vRootView.getContext(), getResources().getString(R.string.copy_buffer), Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }
}
