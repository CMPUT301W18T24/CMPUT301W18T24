/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Created by Fanjie on 2018-04-03.
 */

public class SearchActivityAdapter extends RecyclerView.Adapter<SearchActivityAdapter.ViewHolder> {
    private TaskList taskList;
    private Context context;
    private UserTask chosenTask;


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public SearchActivityAdapter(TaskList taskList, Context context) {
        this.taskList = taskList;
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
        final UserTask task=taskList.getTask(position);
        holder.textTitle.setText(task.getTitle());
        holder.textStatus.setText("Status: "+task.getStatus());

        holder.textOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.textOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.cardview_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            // triggered when edit popup button clicked
                            case R.id.mnu_item_view:
                                chosenTask= taskList.getTask(position);
                                Intent myIntent = new Intent(context, ViewTaskActivity.class);
                                myIntent.putExtra("UserTask",chosenTask);
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
     *  This function returns the number of items in the subscription list
     * @return ArrayList of subscriptions size
     */
    @Override
    public int getItemCount() {
        return taskList.getCount();
    }

    /**
     * This class is responsible for setting item view and data on its place in the recyclerView
     *
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;
        public TextView textStatus;
        public TextView textOption;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.taskTitle);
            textStatus = (TextView) itemView.findViewById(R.id.status);
            textOption = (TextView) itemView.findViewById(R.id.textOption);
        }
    }
}

