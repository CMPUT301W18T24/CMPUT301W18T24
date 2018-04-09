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
 */

public class ViewTaskActivity extends AppCompatActivity {
    private FireBaseManager fmanager;
    private TextView titleText;
    private TextView descriptionText;
    private TextView dateText;
    private TextView usernameText;
    private Button button;
    private Button button_delete;
    private UserTask task;
    private ProgressDialog mProgress;
    private boolean taskOwenr = false;
    private boolean assignedTask = false;
    private BidList bidList = new BidList();
    private RecyclerView bids;
    private BidsViewAdapter bidsAdapter;
    private ImageButton photoButton;
    private ArrayList<Bitmap> bitmaps;
    private AlertDialog.Builder builder;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        final UserSingleton singleton = UserSingleton.getInstance();
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());
        mProgress = new ProgressDialog(this);

        task = (UserTask) getIntent().getSerializableExtra("UserTask");

        bitmaps = getBitmaps();
        Button button_viewLocation = (Button) findViewById(R.id.button_viewLocation);
        button = (Button) findViewById(R.id.button_viewTask);
        button_delete = (Button) findViewById(R.id.button_delete);
        titleText = (TextView) findViewById(R.id.textView_titleContent);
        descriptionText = (TextView) findViewById(R.id.textView_descriptionContent);
        usernameText = (TextView) findViewById(R.id.textView_username);
        dateText = (TextView) findViewById(R.id.textView_dateContent);
        bids = (RecyclerView) findViewById(R.id.bidList);
        bids.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        photoButton = findViewById(R.id.photo_button);
        if (!bitmaps.isEmpty())
            photoButton.setImageBitmap(bitmaps.get(0));


        if (task.getRequester().equals(singleton.getUserId())) {
            taskOwenr = true;
        } else if (task.getAssigned()) {
            assignedTask = true;
        }


        // @See BidDialog.java
        final BidDialog bidDialog = new BidDialog(this, task, new BidDialog.onDialogListener() {
            @Override
            public void onEnsure(String amount) {
                Bid bid = new Bid(task.getId(), valueOf(amount), singleton.getUserId(), singleton.getUserName(), task.getRequesterName());
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

        final AlertDialog alertDialog = buildAlertDialog();

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
                        Intent editIntent = new Intent(getApplicationContext(), EditTaskActivity.class);
                        editIntent.putExtra("UserTask", task);
                        startActivity(editIntent);

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
                fmanager.getTaskInfo(task.getId(), new OnGetATaskListener() {
                    @Override
                    public void onSuccess(UserTask t) {
                        Log.d("tag", "onSuccess: " + t.getId());
                        LatLng latLng = new LatLng(t.getLatitude(), t.getLongtitude());
                        Intent mIntent = new Intent(getApplicationContext(), MapsActivity.class).putExtra("LatLng", latLng);
                        mIntent.putExtra("Mode","View");
                        startActivity(mIntent);
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
            }
        });


        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmaps.size() > 0) {
                    Log.d("Photo count", task.getPhotoUriString().toString());
                    openPhotoSlider();
                } else {
                    Log.d("Photo count", task.getPhotoUriString().toString());
                    Toast.makeText(getApplicationContext(), "This Task has no Photos", Toast.LENGTH_LONG).show();
                }
            }
        });

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
                Toast.makeText(getApplicationContext(), "Cliked", Toast.LENGTH_LONG).show();
            }
        });
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
        if (taskOwenr) {
            button.setText("EDIT");
            if (task.getStatus().equals("Requested")) {
                button_delete.setVisibility(View.VISIBLE);
            }
        } else if (assignedTask) {
            button.setText("COMPLETE");
        }else {
            button.setText("BID");
        }
        usernameText.setText(task.getRequesterName());
        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());
        dateText.setText(task.getDate());
        fmanager.getBidsListOnTask(task.getId(), new OnGetBidsListListener() {
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
                    bidsAdapter = new BidsViewAdapter(bidList, getApplicationContext());
                    bids.setAdapter(bidsAdapter);
                    bidsAdapter.notifyDataSetChanged();
                }
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
                fmanager.removeTask(task.getId());
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
}
