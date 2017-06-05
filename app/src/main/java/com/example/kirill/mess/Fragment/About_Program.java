package com.example.kirill.mess.Fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirill.mess.R;


/**
 * Created by Kirill on 05.01.2017.
 */

public class About_Program extends Fragment implements View.OnClickListener {
    private View vRootView;
    private CardView vCardMail, vCardBank;
    private TextView vTextMail, vTextBank;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vRootView = inflater.inflate(R.layout.fragment_about_program, container, false);
        vTextMail = (TextView)vRootView.findViewById(R.id.about_mail_textView);
        vTextBank = (TextView)vRootView.findViewById(R.id.about_bank_textView);

        vCardMail = (CardView)vRootView.findViewById(R.id.about_mail_cardView);
        vCardMail.setOnClickListener(this);

        vCardBank = (CardView)vRootView.findViewById(R.id.about_bank_cardView);
        vCardBank.setOnClickListener(this);

        return vRootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.about_mail_cardView:
                ClipboardManager clipboard = (ClipboardManager) vRootView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", "aleksanov.96@gmail.com");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(vRootView.getContext(), getResources().getString(R.string.copy_adress_mail) ,Toast.LENGTH_SHORT ).show();
                break;
            case R.id.about_bank_cardView:
                ClipboardManager clipboard2 = (ClipboardManager) vRootView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip2 = ClipData.newPlainText("","4276 8080 2986 9919");
                clipboard2.setPrimaryClip(clip2);
                Toast.makeText(vRootView.getContext(), getResources().getString(R.string.copy_bank) ,Toast.LENGTH_SHORT ).show();
                break;
        }
    }
}
