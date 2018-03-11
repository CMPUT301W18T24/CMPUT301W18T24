package com.example.taskmagic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by hyusuf on 2018-03-11.
 */

public class CreateTaskActivity extends AppCompatActivity {
    private UserTask task;
    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefeed);
    }


}
