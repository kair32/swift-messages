package com.example.kirill.mess.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.example.kirill.mess.Constants.Constants;
import com.example.kirill.mess.DialogFragment.Create_Password;
import com.example.kirill.mess.R;

import static android.content.Context.MODE_PRIVATE;

public class Setting_Fragment extends Fragment implements View.OnClickListener{

    private View vRootView;
    private Switch vSwitchPass, vSwitchFingerPr;
    private Button vUsePassButton, vButtonCancel, vButtonSave;
    private SharedPreferences mSettings;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vRootView =  inflater.inflate(R.layout.fragment_setting, container, false);

        vSwitchPass = (Switch)vRootView.findViewById(R.id.switch_use_password);
        vSwitchPass.setOnClickListener(this);

        vSwitchFingerPr = (Switch)vRootView.findViewById(R.id.switch_use_fingerprint);

        vUsePassButton = (Button) vRootView.findViewById(R.id.button_password);
        vUsePassButton.setOnClickListener(this);

        vButtonCancel = (Button)vRootView.findViewById(R.id.setting_cancel);
        vButtonCancel.setOnClickListener(this);

        vButtonSave = (Button)vRootView.findViewById(R.id.setting_save_button);
        vButtonSave.setOnClickListener(this);

        mSettings = getActivity().getSharedPreferences(Constants.SETTING_NAME, Context.MODE_PRIVATE);
        vSwitchPass.setChecked(mSettings.getBoolean("HavePass",false));
        vSwitchFingerPr.setChecked(mSettings.getBoolean("FingetPrint",false));
        return vRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.switch_use_password:
                if(vSwitchPass.isChecked()){
                Create_Password fragment1 = new Create_Password();
                fragment1.show(getActivity().getFragmentManager(), "Password");}
                break;
            case R.id.setting_cancel:
                getActivity().onBackPressed();
                break;
            case R.id.setting_save_button:
                SharedPreferences.Editor editors = this.getActivity().getSharedPreferences(Constants.SETTING_NAME, MODE_PRIVATE).edit();
                editors.putBoolean("FingetPrint", vSwitchFingerPr.isChecked());
                editors.putBoolean("HavePass", vSwitchPass.isChecked());
                editors.commit();
                getActivity().onBackPressed();
                break;
            case R.id.button_password:
                Create_Password fragment = new Create_Password();
                fragment.show(getActivity().getFragmentManager(), "Password");
                break;
        }
    }
}
