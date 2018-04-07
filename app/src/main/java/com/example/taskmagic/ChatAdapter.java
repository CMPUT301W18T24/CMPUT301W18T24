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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ChatAdapter extends RecyclerView.Adapter  {
    private ArrayList<ChatMessage> mChatList;
    private Context mContext;
    private final static int ITEM_TYPE_SENT=0;
    private final static int ITEM_TYPE_RECEIVED=1;




    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(ArrayList<ChatMessage> chatMessages, Context context) {
        mChatList = chatMessages;
        mContext = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view;
        if (viewType == ITEM_TYPE_SENT) {
           // RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
          //  params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent,parent,false);

            return new SentMessageHolder(view);

        } else if (viewType == ITEM_TYPE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_recieved,parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null; // view holder for header items
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ChatMessage chatmessage = mChatList.get(position);
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_SENT:
                ((SentMessageHolder) holder).bind(chatmessage);
                break;
            case ITEM_TYPE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(chatmessage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        UserSingleton singleton=UserSingleton.getInstance();
        if (mChatList.get(position).getSenderId().equals(singleton.getUserId())) {
            return ITEM_TYPE_SENT;
        } else {
            return ITEM_TYPE_RECEIVED;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.message_body);
            timeText = (TextView) itemView.findViewById(R.id.message_time);
        }

        void bind(ChatMessage message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getDate().split(" ")[3]);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.item_message_body_text_view);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);

        }
        void bind(ChatMessage chatMessage) {
            messageText.setText(chatMessage.getMessage());
            timeText.setText(chatMessage.getDate().split(" ")[3]);
            nameText.setText(chatMessage.getSenderName());

        }
    }

}
