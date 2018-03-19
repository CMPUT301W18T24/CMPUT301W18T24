package com.example.taskmagic;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by Harrold on 2018-02-21.
 *
 * This photo will take in a Bitmap object as a parameter and resize it to specified size.
 */

public class Photo {

    final int photoSize = 65536;
    private final static int maxHeight = 255;
    private final static int maxWidth = 255;

    private Bitmap bitmap = null;
    private String photoUri;



    //private String encodedPhoto;      /*probably comes with base64*/

    public Photo(Bitmap bitmap) {
        this.bitmap = resizeBitmap(bitmap);
    }

    public Photo() {

    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = resizeBitmap(bitmap);
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    } /*might need to resize for returning; might not*/

    private Bitmap resizeBitmap(Bitmap bitmap) {
        //this method will resize the given Bitmap and set this.image as the resized one
        return Bitmap.createScaledBitmap(bitmap, maxWidth, maxHeight, true);
    }

    public byte[] getByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        return byteArray;
    }

  /*  public void uploadBytes(StorageReference imageRef, Context activityContext) {
        UploadTask uploadTask = imageRef.putBytes(this.getByteArray());
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activityContext, "Upload unsuccessful.", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                photoUri = taskSnapshot.getDownloadUrl().toString();
            }
        });
    }*/
}