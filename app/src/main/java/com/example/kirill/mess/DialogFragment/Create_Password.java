package com.example.kirill.mess.DialogFragment;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kirill.mess.Constants.Constants;
import com.example.kirill.mess.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Kirill on 09.01.2017.
 */

public class Create_Password extends DialogFragment implements View.OnClickListener {
    private View vRootView;
    private EditText vEdit1, vEdit2;
    private TextView vText;
    private Button vbutOk,vButCan;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vRootView = inflater.inflate(R.layout.dialog_fragment_create_password, container, false);
        vEdit1 = (EditText)vRootView.findViewById(R.id.create_password_editText);
        vEdit2 = (EditText)vRootView.findViewById(R.id.create_password_editText2);
        vbutOk = (Button)vRootView.findViewById(R.id.create_password_button_ok);
        vbutOk.setOnClickListener(this);
        vButCan = (Button)vRootView.findViewById(R.id.create_password_button_cancel);
        vButCan.setOnClickListener(this);
        vText = (TextView)vRootView.findViewById(R.id.create_password_textView);
        return vRootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_password_button_ok:
                if(vEdit1.getText().toString().equals(vEdit2.getText().toString())){
                    vText.setText("ОК");
                    Constants.mBoolHavePassvord = true;
                    vText.setTextColor(Color.GREEN);
                    SharedPreferences.Editor editors = this.getActivity().getSharedPreferences(Constants.SETTING_NAME, MODE_PRIVATE).edit();
                    editors.putString("Password", vEdit1.getText().toString());
                    editors.commit();
                    dismiss();
                }
                else {
                    vText.setText("Не совпадает");
                    vText.setTextColor(Color.RED);
                    vEdit1.setText("");
                    vEdit2.setText("");
                }
            break;
            case R.id.create_password_button_cancel:
                dismiss();
            break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
