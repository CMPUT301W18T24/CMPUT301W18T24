/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView mChatsRecyclerView;
    private EditText mMessageEditText;
    private ImageButton mSendImageButton;
    private LinearLayoutManager mLayoutManager;
    private FirebaseAuth auth;
    private FirebaseDatabase mMessagesDBRef;
    private FireBaseManager fmanager;
    private User receiver;
    private UserSingleton singleton;
    private ChatAdapter adapter;
    private Toolbar mChatToolBar;
    private Set<String> users;
    private String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mChatToolBar=(Toolbar) findViewById(R.id.chat_bar);
        setSupportActionBar(mChatToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mChatsRecyclerView = (RecyclerView) findViewById(R.id.messagesRecyclerView);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendImageButton = (ImageButton) findViewById(R.id.sendMessageImagebutton);
        mChatsRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mChatsRecyclerView.setLayoutManager(mLayoutManager);
        singleton = UserSingleton.getInstance();
        auth = singleton.getmAuth();
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());
        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
        getUser(userId);
        mSendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mMessageEditText.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You must enter a message", Toast.LENGTH_SHORT).show();
                } else {
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Log.d("yo", "onClick: "+singleton.getUserName());
                    ChatMessage chatMessage = new ChatMessage(message, singleton.getUserId(), receiver.getId(), currentDateTimeString,receiver.getUserName(),singleton.getUserName());
                    fmanager.addChatMessage(chatMessage);
                    mMessageEditText.setText(null);
                    hideSoftKeyboard();
                }
            }
        });



    }

    public void chatListener(final String sender, final String receiver) {
        fmanager.retrieveChatMessages(sender, receiver, new OnGetChatMessagesListener() {
            @Override
            public void onSuccess(ArrayList<ChatMessage> chatMessages) {
                updateView(chatMessages);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getApplicationContext(), "Failure to upload messages because " + message, Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        chatListener(singleton.getUserId(), userId);
    }

    public void updateView(final ArrayList<ChatMessage> chatList) {
        adapter = new ChatAdapter(chatList, this);
        mChatsRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void getUser(String userid) {
        fmanager.getUserInfo(userid, new OnGetUserInfoListener() {
            @Override
            public void onSuccess(User user) {
                    receiver=user;
                    getSupportActionBar().setTitle(receiver.getUserName());
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
