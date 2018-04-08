/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
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
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.taskmagic.CreateTaskActivity.MAX_IMG_SIZE;
import static com.example.taskmagic.CreateTaskActivity.maxHeight;
import static com.example.taskmagic.CreateTaskActivity.maxWidth;

/**
 * Created by harrold on 4/7/2018. Originally EditDialog by yuandi
 */

public class EditTaskActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 21;
    private static final int GALLERY_REQUEST = 20;
    private static final int CALENDAR_ID = 41;
    private DatabaseReference db;
    private FireBaseManager fmanager;
    private UserSingleton singleton = UserSingleton.getInstance();
    private Gson gson = new Gson();

    UserTask task;
    ArrayList<Bitmap> bitmaps;
    byte[] photoBytes;
    PhotoList photoUris = new PhotoList();
    private int currYear;
    private int currMonth;
    private int currDay;
    private String dateString;

    EditText titleText;
    EditText descriptionText;
    TextView dateText;
    Button confirmButton;
    Button cancelButton;
    RecyclerView displayRecycler;
    ImageButton cameraButton;
    ImageButton galleryButton;
    private PhotosAdapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        db = FirebaseDatabase.getInstance().getReference();
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());

        task = (UserTask) getIntent().getSerializableExtra("UserTask");
        bitmaps = getBitmaps();

        final Calendar cal = Calendar.getInstance();
        currYear = cal.get(Calendar.YEAR);
        currMonth = cal.get(Calendar.MONTH);
        currDay = cal.get(Calendar.DAY_OF_MONTH);
        dateString = task.getDate();

        titleText = findViewById(R.id.editText_titleContent);
        descriptionText = findViewById(R.id.editText_descriptionContent);
        dateText = findViewById(R.id.editText_dateContent);

        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());

        dateText.setText(task.getDate());
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CALENDAR_ID);
            }
        });

        /**
         * @see CreateTaskActivity
         */
        cameraButton = findViewById(R.id.edit_camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        /**
         * @see CreateTaskActivity
         */
        galleryButton = findViewById(R.id.edit_gallery_button);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGalleryButtonClick(v);
            }
        });

        displayRecycler = findViewById(R.id.edit_display_recycler);
        displayRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        photosAdapter = new PhotosAdapter(bitmaps, photoUris, this);
        displayRecycler.setAdapter(photosAdapter);

        /**
         * @see CreateTaskActivity
         */
        confirmButton = findViewById(R.id.edit_confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields().equals(true)) {
                    task.setTitle(titleText.getText().toString().trim());
                    task.setDescription(descriptionText.getText().toString().trim());
                    task.setDate(dateText.getText().toString().trim());
                    //Jsonify photoUris for saving
                    task.setPhotoUriString(gson.toJson(photoUris));
                    task.setEditing(false);

                    // save userTask to database
                    Log.d("UserTask edited", task.getTitle() + db + fmanager);
                    fmanager.editTask(task);

                    Intent retIntent = new Intent(getApplicationContext(), ViewTaskActivity.class);
                    retIntent.putExtra("UserTask", task);
                    startActivity(retIntent);
                    //go back to homeFeed
                    //https://stackoverflow.com/questions/14059810/go-back-to-mainactivity-when-ok-pressed-in-alertdialog-in-android
                }
            }
        });

        cancelButton = findViewById(R.id.edit_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    /**
     * @see CreateTaskActivity
     * @param view
     */
    private void onGalleryButtonClick(View view) {
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
                // https://stackoverflow.com/questions/28760941/compress-image-file-from-camera-to-certain-size -- 6/April/2018
                int streamLength = MAX_IMG_SIZE;
                int compressQuality = 105;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                while (streamLength >= MAX_IMG_SIZE && compressQuality > 5) {
                    try {
                        outputStream.flush();
                        outputStream.reset();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    compressQuality -= 5;
                    cameraBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, outputStream);
                    photoBytes = outputStream.toByteArray();
                    streamLength = photoBytes.length;
                    if (BuildConfig.DEBUG) {
                        Log.d("Test upload", "Quality: " + compressQuality);
                        Log.d("Test upload", "Size: " + streamLength);
                    }
                }

                photoUris.add(Base64.encodeToString(photoBytes, Base64.DEFAULT));

                photosAdapter.addItem(cameraBitmap);
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
                    // https://stackoverflow.com/questions/28760941/compress-image-file-from-camera-to-certain-size -- 6/April/2018
                    int streamLength = MAX_IMG_SIZE;
                    int compressQuality = 105;
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    while (streamLength >= MAX_IMG_SIZE && compressQuality > 5) {
                        try {
                            outputStream.flush();
                            outputStream.reset();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        compressQuality -= 5;
                        galleryBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, outputStream);
                        photoBytes = outputStream.toByteArray();
                        streamLength = photoBytes.length;
                        if (BuildConfig.DEBUG) {
                            Log.d("Test upload", "Quality: " + compressQuality);
                            Log.d("Test upload", "Size: " + streamLength);
                        }
                    }

                    photoUris.add(Base64.encodeToString(photoBytes, Base64.DEFAULT));

                    photosAdapter.addItem(galleryBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indicating that the image is unavailable
                    Toast.makeText(this, "Unable to open image.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * @see ViewTaskActivity
     * @return
     */
    private ArrayList<Bitmap> getBitmaps() {
        Gson gson = new Gson();
        PhotoList photoList = gson.fromJson(task.getPhotoUriString(), PhotoList.class);

        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (int i = 0 ; i < photoList.getCount() ; i++) {
            photoUris.add(photoList.getPhoto(i));

            byte[] barray = Base64.decode(photoList.getPhoto(i), Base64.DEFAULT);
            bitmaps.add(BitmapFactory.decodeByteArray(barray, 0, barray.length));
        }

        return bitmaps;
    }

    /**
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
            dateText.setText(newDate);
            dateString = newDate;
        }
    };

    /**
     * @see CreateTaskActivity
     * @return boolean
     */
    private Boolean checkFields() {
        if (titleText.getText().length() <= 0 || descriptionText.getText().length() <= 0){
            Log.d("title check", titleText.getText() + (titleText.getText().equals("") ? "empty" : "non empty"));
            Toast.makeText(this, "Please fill empty fields.", Toast.LENGTH_LONG).show();
            return false;
        } else if (titleText.getText().length() > 30) {
            Toast.makeText(this, "Title is too long.", Toast.LENGTH_LONG).show();
            return false;
        } else if (descriptionText.getText().length() > 300) {
            Toast.makeText(this, "Description too long.", Toast.LENGTH_LONG).show();
            return false;
        }

        Log.d("Check input fields", "All fields have valid inputs.");
        return true;
    }
}
