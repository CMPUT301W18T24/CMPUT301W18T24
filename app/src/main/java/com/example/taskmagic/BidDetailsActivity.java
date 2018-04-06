/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.String.valueOf;

public class BidDetailsActivity extends AppCompatActivity {
    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;
    private Bid bid;
    private UserTask task;
    private TextView title;
    private TextView description;
    private TextView provider;
    private TextView amount;
    private TextView lowestAmount;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_details);
        final UserSingleton singleton = UserSingleton.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        auth = singleton.getmAuth();
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());
        userID = singleton.getUserId();

        title = (TextView) findViewById(R.id.textView_titleContent);
        description = (TextView) findViewById(R.id.textView_descriptionContent);
        provider = (TextView) findViewById(R.id.textView_userContent);
        amount = (TextView) findViewById(R.id.textView_amountContent);
        lowestAmount = (TextView) findViewById(R.id.textView_lowestBidContent);

        bid = (Bid)getIntent().getSerializableExtra("Bid");

        final Button button_accept = (Button) findViewById(R.id.button_accept);
        Button button_contact = (Button) findViewById(R.id.button_contact);

        initView();

        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bid.acceptBid();
                task.setStatus("Assigned");
                task.setProvider(bid.getProvider());
                task.setAssigned(true);
                fmanager.editTask(task);
                fmanager.editBid(bid);
                Toast toast = Toast.makeText(getApplicationContext(), "Accepted.", Toast.LENGTH_LONG);
                button_accept.setEnabled(false);

            }
        });
    }

    /**
     * Initiates views of activity
     */
    private void initView() {
        fmanager.getTaskInfo(bid.getTaskID(), new OnGetATaskListener() {
            @Override
            public void onSuccess(UserTask t) {
                task = t;
                title.setText(t.getTitle());
                description.setText(t.getDescription());
                lowestAmount.setText(valueOf(task.getLowestBid()));
                if (t.isAssigned() || !t.getRequester().equals(userID)) {
                    Button button = (Button) findViewById(R.id.button_accept);
                    button.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(String message) {
                Toast toast = Toast.makeText(getApplicationContext(), "Task Not Found!", Toast.LENGTH_LONG);
                toast.show();
                finish();
            }
        });


        fmanager.getUserInfo(bid.getProvider(), new OnGetUserInfoListener() {
            @Override
            public void onSuccess(User u) {
                provider.setText(u.getFullName());
            }

            @Override
            public void onFailure(String message) {
                Toast toast = Toast.makeText(getApplicationContext(), "User Not Found!", Toast.LENGTH_LONG);
                toast.show();
                finish();
            }
        });
        amount.setText(valueOf(bid.getAmount()));
    }
}
