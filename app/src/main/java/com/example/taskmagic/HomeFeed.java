package com.example.taskmagic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hyusuf on 2018-03-08.
 */

public class HomeFeed extends AppCompatActivity {
    private UserTask task;
    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;
    private ProgressDialog mProgress;
    private String userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefeed);
        final UserSingleton singleton=UserSingleton.getInstance();
        db= FirebaseDatabase.getInstance().getReference();
        auth=singleton.getmAuth();
        userID = singleton.getUserId();
        Log.d("homefeed", "onCreate: "+singleton.getmAuth()+ db);
        fmanager=new FireBaseManager(singleton.getmAuth(),db,getApplicationContext());
        mProgress = new ProgressDialog(this);

        //fmanager.addBid(bid);
        //listener(singleton.getUserId());
        Button buttonMyTask = (Button) findViewById(R.id.button_Test);
        buttonMyTask.setText("Suppose this is my own task.");
        Button buttonNotMyTask = (Button) findViewById(R.id.button_Test2);
        buttonNotMyTask.setText("Suppose this is NOT my own task.");

        final BidDialog bidDialog = new BidDialog(this, task, new BidDialog.onDialogListener() {
            @Override
            public void onEnsure(String amount) {
                Bid bid = new Bid(task.getId(), Float.valueOf(amount), userID);
                addBid(bid);
            }
        });
        buttonMyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new UserTask("Need for a drive", "I need a drive from UofA to WEM at 4 pm.", "User001");
                task.setRequester(singleton.getUserId());
                Intent intent = new Intent(getApplicationContext(), ViewTaskActivity.class);
                intent.putExtra("task",1);
                startActivity(intent);
            }
        });

        buttonNotMyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                task = new UserTask("Need for a drive", "I need a drive from UofA to WEM at 4 pm.", "User001");
//                task.setRequester("1");
                Intent intent = new Intent(getApplicationContext(), ViewTaskActivity.class);
                intent.putExtra("task",2);
                startActivity(intent);

            }
        });
    }

    private void addBid(Bid bid) {
        mProgress.setMessage("Adding");
        mProgress.show();
        fmanager.addBid(bid);
        mProgress.dismiss();
    }

    private void listener(final String requestor) {
        fmanager.getUserInfo(requestor, new OnGetUserInfoListener() {
            @Override
            public void onSuccess(User user) {
                Log.d("TAG", "onSuccess: "+user.getFullName());

            }

            @Override
            public void onFailure(String message) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
