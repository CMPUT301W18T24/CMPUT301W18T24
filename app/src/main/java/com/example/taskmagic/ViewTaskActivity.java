package com.example.taskmagic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

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
    private TextView dateText;
    private Button button;
    private UserTask task;
    private ProgressDialog mProgress;
    private Boolean taskOwenr;
    private BidList bidList = new BidList();
    private RecyclerView bids;
    private RecyclerView.Adapter adapter;
    private ImageButton photoButton;
    private ArrayList<Bitmap> bitmaps;

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

        bitmaps = getBitmaps();
        Button button_viewLocation = (Button) findViewById(R.id.button_viewLocation);
        button = (Button) findViewById(R.id.button_viewTask);
        titleText = (TextView) findViewById(R.id.textView_titleContent);
        descriptionText = (TextView) findViewById(R.id.textView_descriptionContent);
        dateText = (TextView) findViewById(R.id.textView_dateContent);
        bids = (RecyclerView) findViewById(R.id.bidList);
        bids.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        photoButton = findViewById(R.id.photo_button);
        if (!bitmaps.isEmpty())
            photoButton.setImageBitmap(bitmaps.get(0));


        if (task.getRequester().equals(singleton.getUserId())) {
            taskOwenr = true;

        } else {
            taskOwenr = false;
            Log.d("viewtask", "onCreate: "+task.getRequester()+singleton.getUserId());
        }

        // @See BidDialog.java
        final BidDialog bidDialog = new BidDialog(this, task, new BidDialog.onDialogListener() {
            @Override
            public void onEnsure(String amount) {
                Bid bid = new Bid(task.getId(), Float.valueOf(amount), singleton.getUserId());
                bid.setTaskTitle(task.getTitle());
                bid.setRequestor(task.getRequester());
                bidOnTask(bid);
                task.setBidding(false);
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
        initView();
        /**
        * Depending if User is the task owner, the Button will either Edit task or add Bid
        */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskOwenr) {
                    if (task.allowEditing()) {
                        task.setEditing(true);      //lock the task for editing
                        fmanager.editTask(task);
                        editDialog.show();

                    } else {
                        //shows message that you're not allowed to edit this task
                    }

                } else {
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
    }

    /**
     * Initialize the View of the activity, sets Task details into respective fields
     */
    private void initView() {
        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());
        dateText.setText(task.getDate());
        fmanager.getBidsListOnTask(task.getId(), new OnGetBidsList() {
            @Override
            public void onSuccess(BidList Bids) {
                bidList = Bids;
                bidList.sortList();
                adapter = new BidsAdapter(bidList, getApplicationContext());
                bids.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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
        if (taskOwenr) {
            button.setText("EDIT");
        } else {
            button.setText("BID");
        }
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


    private ArrayList<Bitmap> getBitmaps() {
        Gson gson = new Gson();
        PhotoList photoList = gson.fromJson(task.getPhotoUriString(), PhotoList.class);

        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (int i = 0 ; i < photoList.getCount() ; i++) {
            byte[] barray = Base64.decode(photoList.getPhoto(i), Base64.DEFAULT);
            bitmaps.add(BitmapFactory.decodeByteArray(barray, 0, barray.length));
        }

        return bitmaps;
    }
}
