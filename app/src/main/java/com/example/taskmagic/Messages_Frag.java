/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.nearby.messages.Messages;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages_Frag extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private FireBaseManager fmanager;
    private DatabaseReference db;
    private ArrayList<Messages> messages;
    private List<ChatMessage>values;
    private final static String MessagesTag="messages";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messages_frag, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.MRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserSingleton singleton=UserSingleton.getInstance();
        db=FirebaseDatabase.getInstance().getReference();
        fmanager = new FireBaseManager(singleton.getmAuth(), getActivity());
        chatListener(singleton.getUserId());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Assigned frag item: ", "clicked " + position);
                ChatMessage message= values.get(position);
                Intent myIntent = new Intent(getActivity(), MessageActivity.class);
                myIntent.putExtra("id",message.getSenderId());
                startActivity(myIntent);
            }
        }));

        return view;

    }
    public void chatListener(final String sender) {
        db.child(MessagesTag).child(sender).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,ChatMessage> map =new HashMap<String,ChatMessage>();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    ChatMessage message=snap.getValue(ChatMessage.class);
                    if(message.getReceiverId().equals(sender) && !map.containsKey(message.getSenderId())){
                        map.put(message.getSenderId(),message);
                    }
                    else if(message.getReceiverId().equals(sender) && map.containsKey(message.getSenderId())){
                        map.put(message.getSenderId(),message);
                    }
                }
                updateView(map);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateView(HashMap<String,ChatMessage> map){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        values = new ArrayList<>(map.values());
        adapter=new ChatRoomsAdapter(values,getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
