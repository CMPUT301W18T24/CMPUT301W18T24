package com.example.taskmagic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by hyusuf on 2018-03-11.
 */

public class CreateTaskActivity extends AppCompatActivity {
    private UserTask task;
    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;

    private int whatIsID;

    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;

    private String newTitle;
    private String newDescription;
    private String defaultStatus = "Requested";
    private String taskRequester;
    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private BidList bids = new BidList();

    private EditText titleField;
    private EditText descriptionField;
    private Button addLocationButton;
    private Button addPhotoButton;
    private Button postTaskButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtask);

        titleField = findViewById(R.id.task_title);
        descriptionField = findViewById(R.id.task_description);

        addLocationButton = findViewById(R.id.set_location_button);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add location here
            }
        });

        addPhotoButton = findViewById(R.id.add_photo_button);
        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add photo here
            }
        });

        postTaskButton = findViewById(R.id.post_task_button);
        postTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTitle = titleField.getText().toString();
                newDescription = descriptionField.getText().toString();
                UserTask newTask = new UserTask(whatIsID, newTitle, newDescription, taskRequester, defaultStatus, photos, bids);

                //package newTask and send to wherever.
            }
        });
    }

    //========================================================



    //THESE are not yet done:
    //   - grabbing requester ID
    //   - saving Task into requester's taskList
    //   - setting location
    //   - saving photos
    //
    //   - XML layout is not the prettiest

    // FIX THE CONSTRUCTOR

    //========================================================


}
