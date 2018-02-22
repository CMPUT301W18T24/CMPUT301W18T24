package com.example.taskmagic;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Harrold on 2018-02-22.
 */

public class PhotoTest extends ActivityInstrumentationTestCase2 {

    public PhotoTest() {
        super(com.example.taskmagic.Photo.class);
    }

    //public void      test the constructor

    public void testGetImage() throws Exception {
        Bitmap image = null;

        Photo photo = new Photo(image);

        assertNotNull(photo.image);
    }

    public void testResizeImage() {
        //Bitmap image = get a bitmap image of w/e size
        // int originalSize = get original size of image

        //------------------------
        //place holders for now
        int originalSize = 1;
        int newSize = 2;
        //------------------------

        //resizeImage is called in constructor of Photo
        //Photo photo = new Photo(image);
        //int newSize = photo.image get size

        assertFalse(originalSize == newSize);
    }
}
