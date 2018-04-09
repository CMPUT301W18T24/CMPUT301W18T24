/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This is used to display UserTask information onto a specified ListView
 * Created by Harrold on 2018-03-13.
 * Derived from https://www.youtube.com/watch?v=YMJSBHAZsso -> 2018-Mar-13
 */
public class MyTasksListAdapter extends BaseAdapter {

    private Context mContext;
    private TaskList tasksList;

    public MyTasksListAdapter(Context mContext, TaskList tasksList) {
        this.mContext = mContext;
        this.tasksList = tasksList;
    }

    /**
     * This returns the size of the object's array
     * @return size of array
     */
    @Override
    public int getCount() {
        return tasksList.getCount();
    }

    /**
     * Get item at speecified position
     * @param position in array
     * @return UserTask at position
     */
    @Override
    public Object getItem(int position) {
        return tasksList.getTask(position);
    }

    /**
     * Get the index of current item.
     * @param position
     * @return position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get the view with the information of UserTask at positiion.
     * @param position
     * @param convertView
     * @param parent
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.my_task_list_item, null);
        TextView taskTitle = v.findViewById(R.id.taskTitle);
        TextView taskRequester = v.findViewById(R.id.taskRequester);
        TextView date = v.findViewById(R.id.date);
        ImageView taskThumb = v.findViewById(R.id.taskThumbnail);

        taskTitle.setText(tasksList.getTask(position).getTitle());
        taskRequester.setText(tasksList.getTask(position).getRequester());
        date.setText(tasksList.getTask(position).getDate());
        //taskThumb.setImageBitmap(tasksList.get(position).getPhoto().getImage());

        v.setTag(tasksList.getTask(position).getId());

        return v;
    }
}
