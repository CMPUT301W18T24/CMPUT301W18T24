package com.example.taskmagic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by hyusuf on 2018-03-11.
 */

public class CreateTaskActivity extends AppCompatActivity {

    public static final int GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST = 21;
    public static final int LOCATION_REQUEST = 31;
    public static final int maxHeight = 255;
    public static final int maxWidth = 255;

    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseAuth auth;
    private UserSingleton singleton = UserSingleton.getInstance();

    private String storageTag = "images";
    private String uuid = java.util.UUID.randomUUID().toString();

    private String newTitle;
    private String newDescription;
    private String taskRequester;
    private byte[] photoBArray;
    private String photoUri;

    private EditText titleField;
    private EditText descriptionField;
    private Button addLocationButton;
    private Button openCameraButton;
    private Button openGalleryButton;
    private Button postTaskButton;
    private ImageView thumbnail;

    private CreateTaskActivity thisActivity = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtask);

        titleField = findViewById(R.id.task_title);
        descriptionField = findViewById(R.id.task_description);
        thumbnail = findViewById(R.id.thumbnail);

        db = FirebaseDatabase.getInstance().getReference();
        fmanager = new FireBaseManager(singleton.getmAuth(), getApplicationContext());

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
         * On press of button, the task is uploaded onto database
         */
        postTaskButton = findViewById(R.id.post_task_button);
        postTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newTitle = titleField.getText().toString();
                newDescription = descriptionField.getText().toString();
                taskRequester = singleton.getUserId();

                // save userTask to database
                UserTask newTask = new UserTask(newTitle, newDescription, taskRequester, photoUri);
                Log.d("UserTask created", newTask.getTitle() + db + storageRef + fmanager);
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


                StorageReference imageRef = storageRef.child(storageTag + "/" + uuid + ".png");

                UploadTask uploadTask = imageRef.putBytes(photoBArray);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(thisActivity, "Upload unsuccessful.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        photoUri = taskSnapshot.getDownloadUrl().toString();
                    }
                });

                thumbnail.setImageBitmap(cameraBitmap);
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

                    StorageReference imageRef = storageRef.child(storageTag + "/" + uuid + ".png");

                    UploadTask uploadTask = imageRef.putBytes(photoBArray);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(thisActivity, "Upload unsuccessful.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            photoUri = taskSnapshot.getDownloadUrl().toString();
                        }
                    });

                    thumbnail.setImageBitmap(galleryBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indicating that the image is unavailable
                    Toast.makeText(this, "Unable to open image.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}