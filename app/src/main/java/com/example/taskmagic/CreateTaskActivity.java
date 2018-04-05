package com.example.taskmagic;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This activity is where information is entered to make a new UserTask.
 * Created by hyusuf on 2018-03-11.
 */

public class CreateTaskActivity extends AppCompatActivity {

    public static final int GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST = 21;
    public static final int LOCATION_REQUEST = 31;
    public static final int maxHeight = 255;
    public static final int maxWidth = 255;
    private int CALENDAR_ID = 41;

    private FireBaseManager fmanager;
    private DatabaseReference db;
    private UserSingleton singleton = UserSingleton.getInstance();
    private final Gson gson = new Gson();

    private String newTitle;
    private String newDescription;
    private String taskRequester;
    private byte[] photoBArray;
    private PhotoList photoUris = new PhotoList();
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private int currYear;
    private int currMonth;
    private int currDay;

    private EditText titleField;
    private EditText descriptionField;
    private Button addLocationButton;
    private ImageButton openCameraButton;
    private ImageButton openGalleryButton;
    private RecyclerView displayRecycler;
    private PhotosAdapter adapter;
    private Button postTaskButton;
    private TextView dateField;

    private CreateTaskActivity thisActivity = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtask);

        titleField = findViewById(R.id.task_title);
        descriptionField = findViewById(R.id.task_description);

        db = FirebaseDatabase.getInstance().getReference();
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());

        final Calendar cal = Calendar.getInstance();
        currYear = cal.get(Calendar.YEAR);
        currMonth = cal.get(Calendar.MONTH);
        currDay = cal.get(Calendar.DAY_OF_MONTH);
        dateField = findViewById(R.id.date_field);
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CALENDAR_ID);
            }
        });

        /**
         * On press of button, map opens up and allows user to pin point a location
         */
        addLocationButton = findViewById(R.id.set_location_button);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locationIntent = new Intent(thisActivity, MapsActivity.class);
                startActivityForResult(locationIntent, LOCATION_REQUEST);
                // location is processed in onActivityResult()
            }
        });

        /**
         * On press of button, camera opens up and allows user to take a snapshot
         */
        openCameraButton = findViewById(R.id.camera_button);
        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        /**
         * On press of button, gallery opens up and allows user to pick 1 photo to attach to task
         */
        openGalleryButton = findViewById(R.id.gallery_button);
        openGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGalleryButtonClick(view);
            }
        });

        /**
         * This RecyclerView displays the photos associated with the task being created
         */
        displayRecycler = findViewById(R.id.display_recycler);
        displayRecycler.setLayoutManager(new LinearLayoutManager(thisActivity, LinearLayoutManager.HORIZONTAL, false));
        adapter = new PhotosAdapter(bitmaps, thisActivity);
        displayRecycler.setAdapter(adapter);

        /**
         * On press of button, the task is uploaded onto database
         */
        postTaskButton = findViewById(R.id.post_task_button);
        postTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newTitle = titleField.getText().toString().trim();
                newDescription = descriptionField.getText().toString().trim();
                taskRequester = singleton.getUserId();
                //Jsonify photoUris for saving
                String uris = gson.toJson(photoUris);

                // save userTask to database
                //UserTask newTask = new UserTask(newTitle, newDescription, taskRequester, uris);
                UserTask newTask = new UserTask(newTitle, newDescription, taskRequester, uris);
                Log.d("UserTask created", newTask.getTitle() + db + fmanager);
                fmanager.addTask(newTask);

                Intent intent = new Intent(thisActivity, HomeFeed.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                thisActivity.startActivity(intent);
                //go back to homeFeed
                //https://stackoverflow.com/questions/14059810/go-back-to-mainactivity-when-ok-pressed-in-alertdialog-in-android
            }
        });
    }

    /**
     * This method executes when openGallery button is clicked.
     * https://www.youtube.com/watch?v=wBuWqqBWziU -> 13-Mar-2018
     */
    public void onGalleryButtonClick(View view) {
        // invoke the image gallery using an implicit intent.
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        galleryIntent.setDataAndType(data, "image/*");

        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    /**
     * This method handles results; handles return photos.
     * https://www.youtube.com/watch?v=wBuWqqBWziU -> 13-Mar-2018
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            /**
             * process CAMERA_REQUEST returns
             */
            if (requestCode == CAMERA_REQUEST) {
                Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
                cameraBitmap = Bitmap.createScaledBitmap(cameraBitmap, maxWidth, maxHeight, true);
                // https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                cameraBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                photoBArray = outputStream.toByteArray();      /* This byteArray is the photo to be saved into storage */
                photoUris.add(Base64.encodeToString(photoBArray, Base64.DEFAULT));

                adapter.addItem(cameraBitmap);
            }

            /**
             * process GALLERY_REQUEST returns
             */
            else if (requestCode == GALLERY_REQUEST) {
                final Uri imageUri = data.getData();

                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    Bitmap galleryBitmap = BitmapFactory.decodeStream(inputStream);
                    galleryBitmap = Bitmap.createScaledBitmap(galleryBitmap, maxWidth, maxHeight, true);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    galleryBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    photoBArray = baos.toByteArray();
                    photoUris.add(Base64.encodeToString(photoBArray, Base64.DEFAULT));

                    adapter.addItem(galleryBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indicating that the image is unavailable
                    Toast.makeText(this, "Unable to open image.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /*
     * https://www.youtube.com/watch?v=czKLAx750N0 2/April/2018
     * This opens up a DatePickerDialog and displays chosen date to dateField.
     * @param id
     * @return
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == CALENDAR_ID)
            return new DatePickerDialog(this, dateListener, currYear, currMonth, currDay);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dateListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String newDate = String.format("%d/%d/%d", year, month + 1, dayOfMonth);
            dateField.setText(newDate);
        }
    };
}
