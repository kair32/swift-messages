package com.example.kirill.mess.Firebase;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kirill.mess.Main2Activity;
import com.example.kirill.mess.R;

/**
 * Created by Kirill on 29.11.2016.
 */

public class Adapter_Resyl_class extends RecyclerView.ViewHolder {
    private LinearLayout vLinerLAyout;
    public TextView vNameDialogTextView, vDataTextView;
    public String mKeyDialog;
    public ImageView vDialogImageView;
    /*public Adapter_Resyl_class(){
        super(getContext(), R.layout.adapter_fragment_dialog);

    }*/

        public Adapter_Resyl_class(View v) {
            super(v);
            vLinerLAyout = (LinearLayout)itemView.findViewById(R.id.dialog_fragment_linerlayout);
            //vLinerLAyout.setOnClickListener((View.OnClickListener) this);

            //vLAstMsgTextView = (TextView)itemView.findViewById(R.id.diaLastTextView);
            //vDataTextView = (TextView)itemView.findViewById(R.id.diaDatTextView);
            vNameDialogTextView = (TextView)itemView.findViewById(R.id.diaTextView);
            vDialogImageView = (ImageView)itemView.findViewById(R.id.diaImageView);
        }

}
