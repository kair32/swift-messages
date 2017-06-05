package com.example.kirill.mess.Fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
//На ок создавать диалог и открвыать диалог!

public class Create_Dialog_Fragment extends Fragment implements View.OnClickListener, TextWatcher{
    private View vRootView;
    private CardView vCardView;
    private TextView vTextView;
    private ImageView vCameraIV, vImageView;
    private FloatingActionButton vFAB;
    private EditText vEditText, vEditTextKey;
    private String mDateString;
    private Uri selectedImage;
    private static final int CAMERA_REQUEST = 1888;
    private  final int RQS_IMAGE1 = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vRootView =  inflater.inflate(R.layout.fragment_create__dialog_, container, false);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("ddmmyyyyhhmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/GMT-3"));
        mDateString = sdf.format(date);

        vCardView = (CardView)vRootView.findViewById(R.id.create_dialod_cardViewTab);
        vCardView.setOnClickListener(this);

        vEditTextKey = (EditText)vRootView.findViewById(R.id.create_dialog_editText_key);
        vEditTextKey.setText(mDateString);
        vEditTextKey.setRawInputType(0x00000000);

        vEditText = (EditText)vRootView.findViewById(R.id.create_dialog_editText);
        vEditText.addTextChangedListener(this);

        vCameraIV = (ImageView)vRootView.findViewById(R.id.create_dialog_camera_ImageView);
        vCameraIV.setOnClickListener(this);

        vImageView = (ImageView)vRootView.findViewById(R.id.setting_ImageView);
        vImageView.setOnClickListener(this);

        vFAB = (FloatingActionButton)vRootView.findViewById(R.id.create_dialog_fab);
        vFAB.setOnClickListener(this);
        vFAB.hide();

        return vRootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.create_dialod_cardViewTab:
                ClipboardManager clipboard = (ClipboardManager) vRootView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", vEditTextKey.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(vRootView.getContext(), getResources().getString(R.string.copy_buffer) ,Toast.LENGTH_SHORT ).show();
                break;
            case R.id.create_dialog_camera_ImageView:
                OpenCamera();
                break;
            case R.id.setting_ImageView:
                loadImage();
                break;
            case R.id.create_dialog_fab:
                new LoadStorageClass(vImageView, vEditText.getText().toString(), vEditTextKey.getText().toString());
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

    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().trim().length() > 0) {
            vFAB.show();
        } else {
            vFAB.hide();
        }
    }
    @Override public void afterTextChanged(Editable editable) {}


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