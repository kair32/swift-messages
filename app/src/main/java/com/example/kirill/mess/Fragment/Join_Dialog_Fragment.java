package com.example.kirill.mess.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirill.mess.Constants.Constants;
import com.example.kirill.mess.Parsers.ChatMessage;
import com.example.kirill.mess.Parsers.LoadStorageClass;
import com.example.kirill.mess.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Kirill on 31.12.2016.
 */
//14
public class Join_Dialog_Fragment extends Fragment implements View.OnClickListener, TextWatcher {

    private DatabaseReference mSimpleFireMessageDatabaseReference;
    private FloatingActionButton vButton;
    private TextView vTextView;
    private EditText vEditTextKey, vEditTextName;
    private View vRootView;
    private Uri selectedImage;
    private ImageView vCameraIV,vImageView;

    private static final int CAMERA_REQUEST = 1888;
    private  final int RQS_IMAGE1 = 1;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            vRootView =  inflater.inflate(R.layout.fragment_create__dialog_, container, false);
            vCameraIV = (ImageView)vRootView.findViewById(R.id.create_dialog_camera_ImageView);
            vCameraIV.setOnClickListener(this);

            vImageView = (ImageView)vRootView.findViewById(R.id.setting_ImageView);
            vImageView.setOnClickListener(this);

            vButton = (FloatingActionButton)vRootView.findViewById(R.id.create_dialog_fab);
            vButton.setOnClickListener(this);
            vButton.hide();

            vTextView = (TextView)vRootView.findViewById(R.id.create_dialog_textView);
            vTextView.setText(R.string.enter_unique_dialog);

            vEditTextKey = (EditText)vRootView.findViewById(R.id.create_dialog_editText_key);
            vEditTextKey.addTextChangedListener(this);
            vEditTextKey.setOnClickListener(this);

            vEditTextName = (EditText)vRootView.findViewById(R.id.create_dialog_editText);
            vEditTextName.setOnClickListener(this);
            return vRootView;
        }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.create_dialog_camera_ImageView:
                OpenCamera();
                break;
            case R.id.setting_ImageView:
                loadImage();
                break;
            case R.id.create_dialog_fab:
                if(vEditTextName.getText().length()==0)vEditTextName.setText(getResources().getString(R.string.no_name));
                new LoadStorageClass(vImageView, vEditTextName.getText().toString(), vEditTextKey.getText().toString());
                getActivity().onBackPressed();
                break;
            case R.id.create_dialog_editText_key:
                if(vEditTextKey.length()==14)
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

    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().trim().length() > 13) {
            vButton.show();
        } else {
            vButton.hide();
        }
    }
    @Override
    public void afterTextChanged(Editable editable) {

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
