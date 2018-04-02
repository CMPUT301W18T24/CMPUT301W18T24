package com.example.taskmagic;

import android.graphics.Bitmap;
/**
 * Created by Harrold on 2018-02-21.
 *
 * This photo will take in a Bitmap object as a parameter and resize it to specified size.
 */

public class Photo {

    final int photoSize = 65536;
    private final static int maxHeight = 255;
    private final static int maxWidth = 255;

    private String photoUri;

    public Photo(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    private Bitmap resizeBitmap(Bitmap bitmap) {
        //this method will resize the given Bitmap and set this.image as the resized one
        return Bitmap.createScaledBitmap(bitmap, maxWidth, maxHeight, true);
    }
}