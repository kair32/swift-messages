package com.example.kirill.mess.Fragment;

import android.app.Instrumentation;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kirill.mess.Constants.Constants;
import com.example.kirill.mess.Parsers.ChatMessage;
import com.example.kirill.mess.Parsers.LoadStorageClass;
import com.example.kirill.mess.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Kirill on 02.01.2017.
 */

public class Setting_Message_Fragment extends Fragment implements View.OnClickListener {
    private View vRootView;
    private ImageView vCameraIV, vImageView;
    private EditText vEditTextNameDia;
    private CardView vCardView;
    private Button vButCancel, vButSave;
    private Uri selectedImage;
    private static final int CAMERA_REQUEST = 1888;
    private  final int RQS_IMAGE1 = 1;

    private DatabaseReference mSimpleFireMessageDatabaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vRootView = inflater.inflate(R.layout.fragment_setting_message, container, false);

        vEditTextNameDia = (EditText)vRootView.findViewById(R.id.setting_editText_name);
        vEditTextNameDia.setText(Constants.mNameDialog[Constants.mNumberDialog]);

        vCameraIV = (ImageView)vRootView.findViewById(R.id.setting_camera_ImageView);
        vCameraIV.setOnClickListener(this);

        vImageView = (ImageView)vRootView.findViewById(R.id.setting_ImageView);
        vImageView.setOnClickListener(this);
        Glide
                .with(this)
                .load(Constants.mURLPhoto[Constants.mNumberDialog])
                .error(R.drawable.ic_group_black_maxdp)
                .into(vImageView);

        vCardView =(CardView)vRootView.findViewById(R.id.setting_key_cardViewTab);
        vCardView.setOnClickListener(this);

        vButCancel = (Button)vRootView.findViewById(R.id.setting_mess_cancel);
        vButCancel.setOnClickListener(this);

        vButSave = (Button)vRootView.findViewById(R.id.setting_mess_save_button);
        vButSave.setOnClickListener(this);
        return vRootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.setting_camera_ImageView:
                OpenCamera();
                break;
            case R.id.setting_ImageView:
                loadImage();
                break;
            case R.id.setting_key_cardViewTab:
                ClipboardManager clipboard = (ClipboardManager) vRootView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", Constants.mKeyDialog[Constants.mNumberDialog]);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(vRootView.getContext(), getResources().getString(R.string.copy_buffer) ,Toast.LENGTH_SHORT ).show();
                break;
            case R.id.setting_mess_save_button:
                if(vEditTextNameDia.getText().length()==0)vEditTextNameDia.setText(getResources().getString(R.string.no_name));
                new LoadStorageClass(vImageView, vEditTextNameDia.getText().toString(), Constants.mKeyDialog[Constants.mNumberDialog]);
                getActivity().onBackPressed();
                break;
            case R.id.setting_mess_cancel:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) switch (requestCode) {
            case RQS_IMAGE1:
                selectedImage = data.getData();
                Picasso.with(getContext())
                        .load(selectedImage)
                        .resize(vImageView.getWidth(), vImageView.getHeight())
                        .centerCrop()
                        .into(vImageView);
                break;
            case CAMERA_REQUEST:
                selectedImage = data.getData();
                Picasso.with(getContext())
                        .load(selectedImage)
                        .resize(vImageView.getWidth(), vImageView.getHeight())
                        .centerCrop()
                        .into(vImageView);
                break;
        }
    }
    private void OpenCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
    public void loadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RQS_IMAGE1);
    }
}
