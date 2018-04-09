/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ViewHolder> {
    private List<ChatMessage> chatList;
    private Context mContext;



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView message;

        public ViewHolder(View v) {
            super(v);
            userName = (TextView) v.findViewById(R.id.UserName);
            message= (TextView) v.findViewById(R.id.lastMessage);

        }
    }

    public ChatRoomsAdapter(List<ChatMessage> messages, Context context) {
        chatList = messages;
        mContext = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatRoomsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_message,parent,false);
        return new ChatRoomsAdapter.ViewHolder(view); // view holder for header items
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ChatRoomsAdapter.ViewHolder holder, final int position) {

        ChatMessage chatmessage = chatList.get(position);
        holder.userName.setText(chatmessage.getSenderName());
        holder.message.setText(chatmessage.getMessage());




    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return chatList.size();
    }
}



