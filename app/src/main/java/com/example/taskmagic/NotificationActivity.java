package com.example.taskmagic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by hyusuf on 2018-03-11.
 */

public class NotificationActivity extends AppCompatActivity {
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        mFirestore=FirebaseFirestore.getInstance();
    }

}
