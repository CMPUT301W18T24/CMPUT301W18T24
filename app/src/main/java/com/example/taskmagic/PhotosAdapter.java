/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * TO be used in CreateTaskActivity to display photos to be added
 * Created by harrold on 3/29/2018.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private ArrayList<Bitmap> bitmaps;
    private PhotoList photoUris;
    private Context context;

    public PhotosAdapter(ArrayList<Bitmap> bitmaps, PhotoList photoUris, Context context) {
        this.bitmaps = bitmaps;
        this.photoUris = photoUris;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_photo, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Bitmap bitmap = bitmaps.get(position);

        Log.d("bindviewholder", "this was called");
        holder.photo.setImageBitmap(bitmap);

        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmaps.remove(position);
                photoUris.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    public void addItem(Bitmap newBitmap) {
        bitmaps.add(newBitmap);
        notifyDataSetChanged();
        Log.d("adapter addItem", "called " + String.format("%d", bitmaps.size()));
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo;
        private ImageButton delButton;
        private RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            delButton = itemView.findViewById(R.id.delete_photo);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
