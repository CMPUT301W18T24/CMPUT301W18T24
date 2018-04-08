/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This is a list of Uris of encoded photos in Firebase database
 * Created by harrold on 3/29/2018.
 */

public class PhotoList {

    private ArrayList<String> uriList = new ArrayList<String>();

    /**
     * Takes a String uri and adds it to the list
     * @param photoUri
     */
    public void add(String photoUri) { uriList.add(photoUri);  }

    /**
     * Remove a string Uri from the list
     * @param photoUri
     */
    public void remove(String photoUri) {   uriList.remove(photoUri);   }
    public void remove (int position) { uriList.remove(position);   }

    /**
     * Takes in a String uri and checks if the list has that uri
     * @param photoUri
     * @return
     */
    public Boolean hasPhoto(String photoUri) {  return uriList.contains(photoUri);  }

    /**
     * Takes an int index and returns the item in the list at index position
     * @param index
     * @return
     */
    public String getPhoto(int index){  return uriList.get(index);    }

    /**
     * Returns the whole list
     * @return
     */
    public ArrayList<String> getPhotoList() {   return uriList; }

    /**
     * Returns an int equivalent to the number of items in the list
     * @return
     */
    public int getCount() {return uriList.size();   }

    /**
     * This returns the Bitmaps associated with each Uri in uriList
     * @param context
     * @return
     */
    public ArrayList<Bitmap> getPhotos(Context context) {
        ArrayList<Bitmap> photos = new ArrayList<>();
        for (String uri: uriList) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(uri));
                photos.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return photos;
    }

}
