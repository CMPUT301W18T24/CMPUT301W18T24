package com.example.taskmagic;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Harrold on 2018-02-21.
 *
 * This photo will take in a Bitmap object as a parameter and resize it to specified size.
 */

public class Photo extends AsyncTask<Uri,Integer,byte[]> {

    private static final int photoSize = 65536;

    protected Bitmap image;
    private final static String TAG="Photo class";
    public Context context;

    //private String encodedPhoto;      /*probably comes with base64*/

    public Photo(Bitmap bitmap, Context context) {
        if(bitmap!=null) {
            this.image = bitmap;
            this.context=context;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);
    }

    @Override
    protected byte[] doInBackground(Uri... uris) {
        if (image==null){
            try{
                image= MediaStore.Images.Media.getBitmap(context.getContentResolver(),uris[0]);
            }
            catch (IOException e){
                Log.d(TAG, "doInBackground: IOException"+e.getMessage());

            }
        }
        return new byte[0];
    }

    public Photo() {
        this.image = null;
    }


    public Bitmap getImage() {
        return image;
    } /*might need to resize for returning; might not*/

    public Bitmap resize(Bitmap bitmap){
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = photoSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = photoSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}




