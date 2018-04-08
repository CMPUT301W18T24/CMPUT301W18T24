/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationMSG> nList;
    private Context context;
    private NotificationMSG notification;


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public NotificationAdapter(List<NotificationMSG> nList, Context context) {
        this.nList= nList;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item,parent,false);
        return  new ViewHolder(view);
    }
    //binds the data to the view
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NotificationMSG nMessage=nList.get(position);
        holder.TaskTitle.setText(nMessage.getTaskTitle());
        holder.message.setText(nMessage.getMessage());

        holder.textOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.textOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.notification_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            // triggered when edit popup button clicked
                            case R.id.ViewBidder:
                                notification= nList.get(position);
                                Intent myIntent = new Intent(context, ViewProfileActivity.class);
                                myIntent.putExtra("provider",notification.getSenderId());
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(myIntent);
                                break;
                            // triggered when delete popup button clicked
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });
    }

    /**
     *  This function returns the number of
     * @return ArrayList of notification list
     */
    @Override
    public int getItemCount() {
        return nList.size();
    }

    /**
     * This class is responsible for setting item view and data on its place in the recyclerView
     *
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView TaskTitle;
        public TextView message;
        public TextView textOption;

        public ViewHolder(View itemView) {
            super(itemView);
            TaskTitle = (TextView) itemView.findViewById(R.id.taskTitle);
            message = (TextView) itemView.findViewById(R.id.status);
            textOption = (TextView) itemView.findViewById(R.id.textOption);
        }
    }
}


