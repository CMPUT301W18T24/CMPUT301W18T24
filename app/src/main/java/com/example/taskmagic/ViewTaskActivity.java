package com.example.taskmagic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static java.lang.Float.valueOf;

/**
 * Created by hyusuf on 2018-03-11.
 */

public class ViewTaskActivity extends AppCompatActivity {
    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;
    private TextView titleText;
    private TextView descriptionText;
    private TextView dateText;
    private ImageView photo;
    private Button button;
    private Button button_deletTask;
    private UserTask task;
    private ProgressDialog mProgress;
    private boolean taskOwenr = false;
    private boolean assignedTask = false;
    private BidList bidList = new BidList();
    private RecyclerView bids;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        final UserSingleton singleton = UserSingleton.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        auth = singleton.getmAuth();
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());
        mProgress = new ProgressDialog(this);

        task = (UserTask) getIntent().getSerializableExtra("UserTask");

        Button button_viewLocation = (Button) findViewById(R.id.button_viewLocation);
        button_deletTask = (Button) findViewById(R.id.button_delete);


        button = (Button) findViewById(R.id.button_viewTask);
        titleText = (TextView) findViewById(R.id.textView_titleContent);
        descriptionText = (TextView) findViewById(R.id.textView_descriptionContent);
        dateText = (TextView) findViewById(R.id.textView_dateContent);
        photo = (ImageView) findViewById(R.id.imageView1);

        bids = (RecyclerView) findViewById(R.id.bidList);
        bids.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (task.getRequester().equals(singleton.getUserId())) {
            taskOwenr = true;
        } else if (task.isAssigned()) {
            assignedTask = true;
        }

        // @See BidDialog.java
        final BidDialog bidDialog = new BidDialog(this, task, new BidDialog.onDialogListener() {
            @Override
            public void onEnsure(String amount) {
                Bid bid = new Bid(task.getId(), valueOf(amount), singleton.getUserId());
                bid.setTaskTitle(task.getTitle());
                bid.setRequestor(task.getRequester());
                bidOnTask(bid);
                task.setBidding(false);
                task.setLowestBid(valueOf(amount));
                fmanager.editTask(task);
            }
        });

        // @See EditDialog.java
        final EditDialog editDialog = new EditDialog(this, task, new EditDialog.onDialogListener() {
            @Override
            public void onEnsure(String title, String date, String description) {
                //UPDATE EDITING
                task.setDate(date);
                task.setDescription(description);
                task.setTitle(title);
                //task.setPhoto(image);
                task.setEditing(false);
                fmanager.editTask(task);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning!");
        builder.setMessage("Are you sure to delete the task permenatly?");
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //remove task
                fmanager.removeTask(task.getId());
                finish();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
            }
        });
        final AlertDialog alertDialog = builder.create();

        initView();
        /**
        * Depending if User is the task owner, the Button will either Edit task or add Bid
        */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskOwenr) {                    //Edit task
                    if (task.allowEditing()) {
                        task.setEditing(true);      //lock the task for editing
                        fmanager.editTask(task);
                        editDialog.show();

                    } else {
                        //shows message that you're not allowed to edit this task
                    }
                } else if (assignedTask) {          //Complete task
                    task.setStatus("Done");
                    fmanager.editTask(task);
                    finish();

                } else {                            //Bid on task
                    if (task.allowBidding()) {
                        task.setBidding(true);      //lock the task for bidding
                        fmanager.editTask(task);
                        bidDialog.show();
                    } else {
                        //shows message that the task is done or assigned.
                    }
                }
            }
        });

        /**
         * This button allows the User to view geolocation of the Task
         */
        button_viewLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

        button_deletTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
    }

    /**
     * Initialize the View of the activity, sets Task details into respective fields
     */
    private void initView() {
        if (taskOwenr) {
            button.setText("EDIT");
            if(task.getStatus().equals("Requested")) {
                button_deletTask.setVisibility(View.VISIBLE);
            }
        } else if (assignedTask) {
            button.setText("COMPLETE");
        }else {
            button.setText("BID");
        }
        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());
        dateText.setText(task.getDate());
        fmanager.getBidsListOnTask(task.getId(), new OnGetBidsList() {
            @Override
            public void onSuccess(BidList Bids) {
                if (assignedTask) {
                    Bid acceptedBid = Bids.getAcceptedBid();
                    TextView bidTitle = (TextView) findViewById(R.id.textView_bidList);
                    TextView bidContent = (TextView) findViewById(R.id.textView_bidContent);
                    bidTitle.setText("Accepted Bid");
                    bidContent.setText("" + acceptedBid.getAmount());
                } else {
                    bidList = Bids;
                    bidList.sortList();
                    adapter = new BidsAdapter(bidList, getApplicationContext());
                    bids.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
        /*
        if (task.getPhoto() != null) {
            photo.setImageBitmap(task.getPhoto().getImage());
        }
        */

    }

    /**
     * Allows user to add a bid on current task
     * @param bid
     */
    private void bidOnTask(Bid bid) {
        mProgress.setMessage("Adding");
        mProgress.show();
        fmanager.addBid(bid);
        task.setBidded(true);
        task.setStatus("Bidded");
        fmanager.editTask(task);
        mProgress.dismiss();
    }
}
