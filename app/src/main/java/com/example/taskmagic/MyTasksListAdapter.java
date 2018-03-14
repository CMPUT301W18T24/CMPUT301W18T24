package com.example.taskmagic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harrold on 2018-03-13.
 * Derived from https://www.youtube.com/watch?v=YMJSBHAZsso -> 2018-Mar-13
 */

public class MyTasksListAdapter extends BaseAdapter {

    private Context mContext;
    private List<UserTask> tasksList;

    public MyTasksListAdapter(Context mContext, List<UserTask> tasksList) {
        this.mContext = mContext;
        this.tasksList = tasksList;
    }

    @Override
    public int getCount() {
        return tasksList.size();
    }

    @Override
    public Object getItem(int position) {
        return tasksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.my_task_list_item, null);
        TextView taskTitle = v.findViewById(R.id.taskTitle);
        TextView taskRequester = v.findViewById(R.id.taskRequester);
        TextView date = v.findViewById(R.id.date);
        ImageView taskThumb = v.findViewById(R.id.taskThumbnail);

        taskTitle.setText(tasksList.get(position).getTitle());
        taskRequester.setText(tasksList.get(position).getRequester());
        date.setText(tasksList.get(position).getDate());
        taskThumb.setImageBitmap(tasksList.get(position).getPhoto().getImage());

        v.setTag(tasksList.get(position).getId());

        return v;
    }
}
