/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;

import static java.lang.Float.valueOf;

/**
 * Created by hyusuf on 2018-03-11.
 * This activity shows details of a specific task
 *
 */

public class ViewTaskActivity extends AppCompatActivity {
    private FireBaseManager fmanager;
    private TextView titleText;
    private TextView descriptionText;
    private TextView dateText;
    private TextView usernameText;
    private Button button;
    private Button button_delete;
    private Button button_complete;
    private UserTask task;
    private ProgressDialog mProgress;
    private boolean taskOwner = false;
    private boolean assignedTask = false;
    private BidList bidList;
    private RecyclerView bids;
    private BidsViewAdapter bidsAdapter;
    private ImageButton photoButton;
    private ArrayList<Bitmap> bitmaps;
    private AlertDialog.Builder builder;
    private User user;
    private UserSingleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        singleton = UserSingleton.getInstance();
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());
        mProgress = new ProgressDialog(this);

        task = (UserTask) getIntent().getSerializableExtra("UserTask");

        bitmaps = getBitmaps();
        Button button_viewLocation = (Button) findViewById(R.id.button_viewLocation);
        button = (Button) findViewById(R.id.button_viewTask);
        button_delete = (Button) findViewById(R.id.button_delete);
        button_complete = (Button) findViewById(R.id.button_complete);
        titleText = (TextView) findViewById(R.id.textView_titleContent);
        descriptionText = (TextView) findViewById(R.id.textView_descriptionContent);
        usernameText = (TextView) findViewById(R.id.textView_username);
        dateText = (TextView) findViewById(R.id.textView_dateContent);
        bids = (RecyclerView) findViewById(R.id.bidList);
        bids.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        photoButton = findViewById(R.id.photo_button);
        if (!bitmaps.isEmpty())
            photoButton.setImageBitmap(bitmaps.get(0));

        //Check if task belongs to user
        if (task.getRequester().equals(singleton.getUserId())) {
            taskOwner = true;
        }
        //Check if task is assigned
        if (task.getAssigned()) {
            assignedTask = true;
        }


        // @See BidDialog.java
        final BidDialog bidDialog = buildBidDialog();
        final AlertDialog alertDialog = buildAlertDialog();

        initView();

        /**
        * Depending if User is the task owner, the Button will either Edit task or add Bid
        */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskOwner) {                    //Edit task
                    if (task.allowEditing()) {
                        task.setEditing(true);      //lock the task for editing
                        fmanager.editTask(task);
                        Intent editIntent = new Intent(getApplicationContext(), EditTaskActivity.class);
                        editIntent.putExtra("UserTask", task);
                        startActivity(editIntent);
                    } else if (task.getAssigned()){
                        resetTask(task);
                    }
                } else {                            //Bid on task
                    if (task.allowBidding()) {
                        task.setBidding(true);      //lock the task for bidding
                        fmanager.editTask(task);
                        bidDialog.show();
                    }
                }
            }
        });

        /**
         * This button allows user to complete an assigned task
         */
        button_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeTask();
            }
        });


        /**
         * This button allows the User to view geolocation of the Task
         */
        button_viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fmanager.getTaskInfo(task.getId(), new OnGetATaskListener() {
                    @Override
                    public void onSuccess(UserTask t) {
                        if (!(t.getLongtitude() == null) && !(t.getLatitude() == null)) {
                            LatLng latLng = new LatLng(t.getLatitude(), t.getLongtitude());
                            Intent mIntent = new Intent(getApplicationContext(), MapsActivity.class).putExtra("LatLng", latLng);
                            mIntent.putExtra("Mode", "View");
                            startActivity(mIntent);
                        } else {
                            Toast.makeText(ViewTaskActivity.this, "This task does not have a specified location.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
            }
        });

        /**
         * Clicking this button shows photo slider of the task
         */
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmaps.size() > 0) {
                    openPhotoSlider();
                } else {
                    Toast.makeText(getApplicationContext(), "This Task has no Photos", Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * Clicking this button pops up an alert dialog and delete the task if user clicks
         * confirm button
         */
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        usernameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jump to profile page
                showProfile(singleton.getUserId());
            }
        });
    }

    /**
     * This methods reset the task back to be requested or bidded according to its bidlist
     * The task to be reset has to be assigned but not completed
     * @param task
     */
    private void resetTask( UserTask task) {
        fmanager.updateBidList(task.getId());

        fmanager.getBidsList(task.getId(), new OnGetBidsListListener() {
            @Override
            public void onSuccess(BidList Bids) {
                BidList bidList1 = new BidList();
                bidList1 = Bids;
                bidsAdapter = new BidsViewAdapter(bidList1, getApplicationContext());
                bids.setAdapter(bidsAdapter);
                bidsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

            }
        });

        //If there's other bid on task
        if (bidList.getCount() > 1) {
            task.setStatus("Bidded");
            task.setBidded(true);
        } else {  //If there's only one bid that was accepted previously
            task.setStatus("Requested");
            task.setBidded(false);
        }
        task.setAssigned(false);
        task.setProvider("");
        fmanager.editTask(task);
        finish();
    }

    /**
     * This method completes a task
     */
    private void completeTask() {
        task.setStatus("DONE");
        //MAYBE RATING
        fmanager.editTask(task);
        finish();
    }

    /**
     * This method will show the User Profile in a Dialog
     */
    private void showProfile(final String currentUserId) {
        fmanager.getUserInfo(task.getRequester(), new OnGetUserInfoListener() {
            @Override
            public void onSuccess(User u) {
                user = u;
                final ProfileDialog profileDialog = new ProfileDialog(ViewTaskActivity.this, user, currentUserId, new ProfileDialog.onDialogListener() {
                    @Override
                    public void onEnsure() {
                        Intent myIntent = new Intent(ViewTaskActivity.this, MessageActivity.class);
                        myIntent.putExtra("id",user.getId());
                        startActivity(myIntent);
                    }
                });
                profileDialog.show();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }


    /**
     * Initialize the View of the activity, sets Task details into respective fields
     */
    private void initView() {
        assignedTask = task.getAssigned();

        if (taskOwner) {                                         //If user is the task owner
            if (assignedTask) {                                     //If task is assigned
                button.setText("RESET");                                //show reset button
                button.setTextColor(0xffff0000);
                button_delete.setVisibility(View.GONE);
            } else {                                                //If task is not assigned
                button_complete.setVisibility(View.GONE);               //hide complete button
                button.setText("EDIT");                                 //show edit button

                if (task.getStatus().equals("Bidded")) {                //If task is bidded
                    button.setEnabled(false);                               //disable edit button
                }
                if (task.getStatus().equals("Requested")) {             //If task is requested
                    button_delete.setVisibility(View.VISIBLE);              //show delete button
                }
            }

        } else {                                                //If not the task owner, show bid on task button
            button_complete.setVisibility(View.GONE);           //hide complete button
            button_delete.setVisibility(View.GONE);             //hide delete button
            button.setText("BID");
        }

        //Set task detail info
        usernameText.setText(task.getRequesterName());
        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());
        dateText.setText(task.getDate());

        fmanager.getBidsListOnTask(task.getId(), new OnGetBidsListListener() {
            @Override
            public void onSuccess(BidList Bids) {
                if (assignedTask) {                                     //If task is assigned
                    Bid acceptedBid = Bids.getAcceptedBid();            //Show the accepted bid
                    bidList = new BidList();
                    bidList.add(acceptedBid);
                    TextView bidTitle = (TextView) findViewById(R.id.textView_bidList);
                    bidTitle.setText("Accepted Bid");
                } else {                                                //If task is not assigned
                    bidList = Bids;                                     //Show all bids on the task if any
                    bidList.sortList();                                 //Sort Bidlist in an ascending order
                }
                bidsAdapter = new BidsViewAdapter(bidList, getApplicationContext());
                bids.setAdapter(bidsAdapter);
                bidsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

            }
        });

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

    private ViewPager viewPager;
    private LinearLayout displayDots;
    private int dotsCount;
    private ImageView[] dots;


    /**
     * This method opens a dialog with a slider with all the task's photos.
     */
    //https://www.youtube.com/watch?v=GqcFEvBCnIk       4/April/2018
    //https://www.youtube.com/watch?v=plnLs6aST1M       4/April/2018
    //https://www.youtube.com/watch?v=Q2M30NriSsE       5/April/2018
    private void openPhotoSlider() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ViewTaskActivity.this);
        View photoView = getLayoutInflater().inflate(R.layout.dialog_photo_slider, null);

        viewPager = photoView.findViewById(R.id.photo_slider);
        displayDots =  photoView.findViewById(R.id.display_dots);
        PhotoSliderAdapter photoSliderAdapter = new PhotoSliderAdapter(this, bitmaps);
        viewPager.setAdapter(photoSliderAdapter);
        dotsCount = photoSliderAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0 ; i < dotsCount ; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot_yt));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);

            displayDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot_yt));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0 ; i < dotsCount ; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot_yt));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot_yt));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBuilder.setView(photoView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }


    /**
     * This method decodes the byte arrays in the task's PhotoList and returns them in an array list of bitmaps
     * @return
     */
    private ArrayList<Bitmap> getBitmaps(){
        Gson gson = new Gson();
        PhotoList photoList = gson.fromJson(task.getPhotoUriString(), PhotoList.class);

        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (int i = 0 ; i < photoList.getCount() ; i++) {
            byte[] barray = Base64.decode(photoList.getPhoto(i), Base64.DEFAULT);
            bitmaps.add(BitmapFactory.decodeByteArray(barray, 0, barray.length));
        }

        return bitmaps;
    }

    /**
     * This method initializes and AlertDialog that will warn the User before task deletion
     * @return
     */
    protected AlertDialog buildAlertDialog() {

        builder = new AlertDialog.Builder(this);
        builder.setTitle("WARNING!");
        builder.setMessage("Are you sure to delete this task?");
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fmanager.getBidsList(task.getId(), new OnGetBidsListListener() {
                    @Override
                    public void onSuccess(BidList Bids) {
                        if (Bids.getCount() == 1) {
                            Toast.makeText(getApplicationContext(), "You cant delete this task.", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < Bids.getCount(); i++) {
                                fmanager.removeBid(Bids.getBid(i));
                            }
                            fmanager.removeTask(task.getId());
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });

                finish();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task.setEditing(false);      //unlock the task for editing
                fmanager.editTask(task);
            }
        });
        return builder.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    /**
     * @see BidDialog
     * @return
     */
    private BidDialog buildBidDialog() {
        BidDialog bidDialog = new BidDialog(this, task, new BidDialog.onDialogListener() {
            @Override
            public void onEnsure(String amount) {
                Bid bid = new Bid(task.getId(), valueOf(amount), singleton.getUserId(), singleton.getUserName(), task.getRequester());
                bid.setTaskTitle(task.getTitle());
                bid.setRequestor(task.getRequester());
                bidOnTask(bid);
                task.setBidding(false);
                task.setLowestBid(valueOf(amount));
                fmanager.editTask(task);
            }

            @Override
            public void onCancel() {
                task.setBidding(false);
                fmanager.editTask(task);
            }
        });

        return bidDialog;
    }
}
