package com.example.kirill.mess;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kirill.mess.Constants.Constants;

public class ActivityEntryPassword extends AppCompatActivity implements View.OnClickListener  {
    private RadioButton vRadBut1,vRadBut2,vRadBut3,vRadBut4;
    Button but1,but2,but3,but4,but5,but6,but7,but8,but9,but0,butDel,butForgot;
    ImageView vImageViewAcctount;
    TextView vTextViewName;
    private SharedPreferences mSettings;
    private String mString="";
    private String mStringPasvTru = "0000";
    private boolean mBoolFingerPrint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_password);
        mSettings = getSharedPreferences(Constants.SETTING_NAME, Context.MODE_PRIVATE);
        mStringPasvTru = mSettings.getString("Password", null);
        mBoolFingerPrint = mSettings.getBoolean("FingetPrint", false);

        vImageViewAcctount = (ImageView)findViewById(R.id.activity_entry_password_image);
        Glide
                .with(this)
                .load(Constants.UserPhoto)
                .into(vImageViewAcctount);

        vTextViewName = (TextView) findViewById(R.id.about_textView);
        vTextViewName.setText(Constants.UserName);
        but1 = (Button)findViewById(R.id.about_button1);
        but2= (Button)findViewById(R.id.about_button2);
        but3 = (Button)findViewById(R.id.about_button3);
        but4 = (Button)findViewById(R.id.about_button4);
        but5 = (Button)findViewById(R.id.about_button5);
        but6 = (Button)findViewById(R.id.about_button6);
        but7 = (Button)findViewById(R.id.about_button7);
        but8 = (Button)findViewById(R.id.about_button8);
        but9 = (Button)findViewById(R.id.about_button9);
        but0 = (Button)findViewById(R.id.about_button0);
        butDel = (Button)findViewById(R.id.about_button_dell);
        butForgot = (Button)findViewById(R.id.about_button_forgot);
        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);
        but4.setOnClickListener(this);
        but5.setOnClickListener(this);
        but6.setOnClickListener(this);
        but7.setOnClickListener(this);
        but8.setOnClickListener(this);
        but9.setOnClickListener(this);
        but0.setOnClickListener(this);
        butDel.setOnClickListener(this);
        butForgot.setOnClickListener(this);
        vRadBut1 = (RadioButton)findViewById(R.id.about_radioButton1);
        vRadBut2 = (RadioButton)findViewById(R.id.about_radioButton2);
        vRadBut3 = (RadioButton)findViewById(R.id.about_radioButton3);
        vRadBut4 = (RadioButton)findViewById(R.id.about_radioButton4);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.about_button1:
                mString += 1;
                break;
            case R.id.about_button2:
                mString += 2;
                break;
            case R.id.about_button3:
                mString += 3;
                break;
            case R.id.about_button4:
                mString += 4;
                break;
            case R.id.about_button5:
                mString += 5;
                break;
            case R.id.about_button6:
                mString += 6;
                break;
            case R.id.about_button7:
                mString += 7;
                break;
            case R.id.about_button8:
                mString += 8;
                break;
            case R.id.about_button9:
                mString += 9;
                break;
            case R.id.about_button0:
                mString += 0;
                break;
            case R.id.about_button_dell:
                if( mString.length()!=0)mString = mString.substring(0, mString.length()-1);
                break;
            case R.id.about_button_forgot:
                break;
        }
        Log.d("tap", " " + mString);
        RadioButtonClick();
    }
    public void RadioButtonClick(){
        if(mString.length()==0){vRadBut1.setChecked(false); vRadBut2.setChecked(false); vRadBut3.setChecked(false); vRadBut4.setChecked(false);}
        if(mString.length()==1){vRadBut1.setChecked(true); vRadBut2.setChecked(false); vRadBut3.setChecked(false); vRadBut4.setChecked(false);}
        if(mString.length()==2){vRadBut2.setChecked(true);vRadBut3.setChecked(false); vRadBut4.setChecked(false);}
        if(mString.length()==3){vRadBut3.setChecked(true); vRadBut4.setChecked(false);}
        if(mString.length()==4){vRadBut4.setChecked(true);CheckPasv();}
    }
    public void CheckPasv(){
        if(mString.toString().equals(mStringPasvTru)){
            onBackPressed();
            vRadBut1.setChecked(false); vRadBut2.setChecked(false);
            vRadBut3.setChecked(false); vRadBut4.setChecked(false);}
        else {mString="";vRadBut1.setChecked(false); vRadBut2.setChecked(false); vRadBut3.setChecked(false); vRadBut4.setChecked(false);}
    }

    @Override
    public void onBackPressed() {
        if(mString.toString().equals(mStringPasvTru))super.onBackPressed();
        else finishAffinity();
    }
}
