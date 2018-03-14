package com.example.taskmagic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by hyusuf on 2018-03-11.
 */

public class CreateTaskActivity extends AppCompatActivity {

    public static final int GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST = 21;
    public static final int LOCATION_REQUEST = 31;

    private UserTask task;
/*    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth; */

    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;

    private String taskID;
    private String newTitle;
    private String newDescription;
    private String taskRequester;
    private Photo photo = new Photo();
    private BidList bids = new BidList();

    private EditText titleField;
    private EditText descriptionField;
    private Button addLocationButton;
    private Button openCameraButton;
    private Button openGalleryButton;
    private Button postTaskButton;

    private CreateTaskActivity thisActivity = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtask);

        titleField = findViewById(R.id.task_title);
        descriptionField = findViewById(R.id.task_description);

        /**
         * On press of button, map opens up and allows user to pin point a location
         */
        addLocationButton = findViewById(R.id.set_location_button);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locationIntent = new Intent(thisActivity, MapActivity.class);
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
         * On press of button, the task is uploaded onto database
         */
        postTaskButton = findViewById(R.id.post_task_button);
        postTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTitle = titleField.getText().toString();
                newDescription = descriptionField.getText().toString();
                UserTask newTask = new UserTask(taskID, newTitle, newDescription,
                                                taskRequester, photo, bids);

                //consider checking field correction

                //package newTask and send to wherever.
                fmanager.addTask(newTask);

                finish();
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

        // data location
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        //get URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        // set data and type, get all files of that type
        galleryIntent.setDataAndType(data, "image/*");

        // invoke activity and get something back from it
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
            // if we are here, everything processed successfully

            if (requestCode == CAMERA_REQUEST) {
                Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
                photo.setBitmap(cameraBitmap);
            }

            else if (requestCode == GALLERY_REQUEST) {
                //if we are here, we are hearing back from the image gallery
                //the address of the image on the SD card
                Uri imageURI = data.getData();

                //declare a stream to read the image data from the SD card.
                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(imageURI);

                    // get a bitmap from a stream
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);

                    // create new Photo from bitmap
                    photo.setBitmap(imageBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indicating that the image is unavailable
                    Toast.makeText(this, "Unable to open image.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //========================================================



    //THESE are not yet done:
    //   - generate taskID
    //   - grabbing requester ID
    //
    //   - XML layout is not the prettiest

    //========================================================


}
