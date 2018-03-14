package com.example.taskmagic;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by hyusuf on 2018-03-11.
 */

public class ViewTaskActivity extends AppCompatActivity {
    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;
    private TextView titleText;
    private TextView descriptionText;
    private TextView dateText ;
    private Button button;
    private UserTask task;
    private ProgressDialog mProgress;
    private Boolean taskOwenr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        final UserSingleton singleton = UserSingleton.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        auth = singleton.getmAuth();
        fmanager = new FireBaseManager(singleton.getmAuth(),db,getApplicationContext());
        mProgress = new ProgressDialog(this);

        int i = (int) getIntent().getSerializableExtra("task");

        button = (Button) findViewById(R.id.button_viewTask);
        titleText = (TextView) findViewById(R.id.textView_titleContent);
        descriptionText = (TextView) findViewById(R.id.textView_descriptionContent);
        dateText = (TextView) findViewById(R.id.textView_dateContent);

        //if (singleton.getUserId() == task.getRequester()) {
        if (i == 1) {
            taskOwenr = true;
            task = new UserTask("Need for a drive", "I need a drive from UofA to WEM at 4 pm.", singleton.getUserId());
            task.setRequester(singleton.getUserId());
            task.setId("2");
        } else {
            taskOwenr = false;
            task = new UserTask("Need for a CMPUT301 spot", "I need a spot in CMPUT301.", "User001");
            task.setId("1");
            task.setRequester("James");
        }
        final BidDialog bidDialog = new BidDialog(this, task, new BidDialog.onDialogListener() {
            @Override
            public void onEnsure(String amount) {
                Bid bid = new Bid(task.getId(), Float.valueOf(amount), singleton.getUserId());
                bidOnTask(bid);
            }
        });

        final EditDialog editDialog = new EditDialog(this, task, new EditDialog.onDialogListener() {
            @Override
            public void onEnsure(String title, String description, String date, ArrayList<Photo> images) {
                //UPDATE EDITING
            }
        });
        initView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskOwenr) {
                    if (task.allowEditing()) {
                        editDialog.show();
                    } else {
                        //shows message that you're not allowed to edit this task
                    }

                } else {
                    if (task.allowBidding()) {
                        bidDialog.show();
                    } else {
                        //shows message that the task is done or assigned.
                    }
                }
            }
        });
    }

    private void initView() {
        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());
        dateText.setText(task.getDate());
        if (taskOwenr) {
            button.setText("EDIT");
        } else {
            button.setText("BID");
        }
    }

    private void bidOnTask(Bid bid) {
        mProgress.setMessage("Adding");
        mProgress.show();
        fmanager.addBid(bid);
        mProgress.dismiss();
    }
}
