package com.example.taskmagic;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * Created by hyusuf on 2018-03-12.
 */


public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder> {
    private TaskList taskList;
    private Context context;
    private UserTask chosenTask;

    /**
     *
     * @param position
     * @return position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     * @param position
     * @return position
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     *
     * @param taskList
     * @param context
     */
    public TaskRecyclerAdapter(TaskList taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }


    /**
     *
     * @param parent
     * @param viewType
     * @return ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item,parent,false);
        return  new ViewHolder(view);
    }

    /**
     *  binds the data to the view
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final UserTask task=taskList.getTask(position);
        holder.textTitle.setText(task.getTitle());
        holder.textStatus.setText("Status: "+task.getStatus());
        holder.textRequester.setText(task.getRequesterName());
        if (task.getLowestBid() > 0) {
            holder.textLowest.setText(String.format("$%.2f", task.getLowestBid()));
        } else {
            holder.textLowest.setText("$  -- --");
        }

        Gson gson = new Gson();
        PhotoList photos = gson.fromJson(task.getPhotoUriString(), PhotoList.class);
        if (!photos.isEmpty()) {
            byte[] bytes = Base64.decode(photos.getPhoto(0), Base64.DEFAULT);
            holder.taskThumbnail.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        }
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
        public TextView textRequester;
        public TextView textLowest;
        public ImageView taskThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.taskTitle);
            textStatus = (TextView) itemView.findViewById(R.id.status);
            textRequester = itemView.findViewById(R.id.taskRequester);
            textLowest = itemView.findViewById(R.id.taskLowest);
            taskThumbnail = itemView.findViewById(R.id.taskThumbnail);
        }
    }
}
