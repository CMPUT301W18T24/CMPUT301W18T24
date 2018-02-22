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

    public Photo(Bitmap image) {
        resizeImage(image);
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    private void resizeImage(Bitmap image) {
        //this method will resize the given thumbnail and set this.image as the resized one
        //check out base64 and bitmapfactory
    }
}
