package com.example.taskmagic;

import android.graphics.Bitmap;

/**
 * Created by Harrold on 2018-02-21.
 *
 * This photo will take in a Bitmap object as a parameter and resize it to specified size.
 */

public class Photo {

    final int photoSize = 65536;

    protected Bitmap image = null;

    //private String encodedPhoto;      /*probably comes with base64*/

    public Photo(Bitmap image) {
        resizeImage(image);
        this.image = image;
    }

    public Photo() {
        this.image = null;
    }

    public void setBitmap(Bitmap bitmap) {
        resizeImage(bitmap);
        this.image = bitmap;
    }

    public Bitmap getImage() {
        return image;
    } /*might need to resize for returning; might not*/

    private void resizeImage(Bitmap image) {
        //this method will resize the given Bitmap and set this.image as the resized one
        //check out base64 and bitmapfactory
    }

}