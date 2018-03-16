package com.example.taskmagic;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefeed);
        UserSingleton singleton=UserSingleton.getInstance();
        db= FirebaseDatabase.getInstance().getReference();
        auth=singleton.getmAuth();
        Log.d("homefeed", "onCreate: "+singleton.getmAuth()+ db);
        fmanager=new FireBaseManager(singleton.getmAuth(),db,getApplicationContext());
        //fmanager.addBid(bid);
        //

        /**
         * testing for AddTask
         */
        Button addTaskButton = findViewById(R.id.addTask);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeFeed.this, CreateTaskActivity.class);
                startActivity(intent);
            }
        });


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
