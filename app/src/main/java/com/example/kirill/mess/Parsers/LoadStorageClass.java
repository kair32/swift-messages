package com.example.kirill.mess.Parsers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.example.kirill.mess.Constants.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by Kirill on 19.01.2017.
 */

public class LoadStorageClass {
    private DatabaseReference mSimpleFireMessageDatabaseReference;
    public LoadStorageClass(ImageView imageView, String nameDialog, String keyDialog)
    {
        LoadStorage(imageView, nameDialog, keyDialog);
    }
    private void LoadStorage(ImageView imageView, final String nameDialog, final String keyDialog){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://mess-e1da7.appspot.com");
        StorageReference spaceRef = storageRef.child("image" + Constants.UserName + keyDialog);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = spaceRef.putBytes(data);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
                Log.d("TAP", " " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
                Log.d("TAP", " paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                mSimpleFireMessageDatabaseReference = FirebaseDatabase.getInstance().getReference();
                ChatMessage friendlyMessage = new ChatMessage(nameDialog,keyDialog,Constants.UserPhoto);
                mSimpleFireMessageDatabaseReference.child(Constants.UserEmail).child(keyDialog).setValue(friendlyMessage);
                Log.d("TAP", " " + exception.toString() + " " + exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                mSimpleFireMessageDatabaseReference = FirebaseDatabase.getInstance().getReference();
                ChatMessage friendlyMessage = new ChatMessage(nameDialog,keyDialog,downloadUrl.toString());
                mSimpleFireMessageDatabaseReference.child(Constants.UserEmail).child(keyDialog).setValue(friendlyMessage);
            }
        });
    }
}
