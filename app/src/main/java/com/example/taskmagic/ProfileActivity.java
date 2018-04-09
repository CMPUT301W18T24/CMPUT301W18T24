/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.*;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by hyusuf on 2018-03-08.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 4;

    private EditText mUserName;
    private EditText mEmail;
    private EditText mPhoneNumber;
    private EditText mFullName;
    private EditText mPassword;
    private String UserName;
    private String PassWord;
    private String Email;
    private ImageView image;
    private String PhoneNumber;
    private String FullName;
    private Button mRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Boolean registered;
    private boolean unique;
    private ProgressDialog mProgress;
    private ArrayList<String> userNames;
    private Bitmap bitmap;
    private String userId;
    private boolean googleUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mEmail = (EditText) findViewById(R.id.editTextEmail);
        mPassword = (EditText) findViewById(R.id.editTextPass);
        mFullName = (EditText) findViewById(R.id.editTextfName);
        mPhoneNumber = (EditText) findViewById(R.id.editTextPhone);
        mUserName = (EditText) findViewById(R.id.editTextUserName);
        mRegister = (Button) findViewById(R.id.buttonsave);
        image = (ImageView) findViewById(R.id.imageview);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        userNames=new ArrayList<String>();

        try{
            Intent intent= getIntent();
            mUserName.setText(intent.getStringExtra("userName"));
            mFullName.setText(intent.getStringExtra("name"));
            userId=intent.getStringExtra("id");
            mEmail.setText(intent.getStringExtra("email"));
            mPassword.setVisibility(View.GONE);
            googleUser=true;


        }catch (Exception e){

        }

        checkUserName();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoSelection();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = mEmail.getText().toString();
                PassWord = mPassword.getText().toString();
                FullName = mFullName.getText().toString();
                PhoneNumber = mPhoneNumber.getText().toString();
                UserName = mUserName.getText().toString();
                if (Email.isEmpty() || PassWord.isEmpty()&& googleUser==false || FullName.isEmpty() || PhoneNumber.isEmpty() || UserName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "One Of The Fields Are Empty Please Complete Your Profile", Toast.LENGTH_SHORT).show();
                    return;
                } else if (UserName.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Sorry Your UserName Must Have At Least 8 Characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkUniqueness(UserName)){
                    Toast.makeText(getApplicationContext(), "Sorry That UserName Is In Use", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (googleUser){
                    User user = new User(FullName, Email, UserName, PhoneNumber);
                    user.setId(userId);
                    String token= FirebaseInstanceId.getInstance().getToken();
                    user.setToken(token);
                    createGoogleUser(user);
                }else{
                    User user1 = new User(FullName, Email, UserName, PassWord, PhoneNumber);
                    emailUser(user1);
                }

            }
        });


    }

    private void createGoogleUser(User user) {
        mDatabase.child("users").child(user.getId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Saved your account",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private boolean checkUniqueness(String userName) {
        if(userNames.size()!= 0){
            for(int i=0; i<userNames.size();i++) {
                if (userNames.get(i)!=null) {
                    String name = userNames.get(i);
                    if (name.equals(userName)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void photoSelection() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");

        //SET ITEMS AND THERE LISTENERS
        final AlertDialog.Builder builder1 = builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }



    public void emailUser(final User user) {
        mProgress.setMessage("Saving");
        mProgress.show();
        mAuth.createUserWithEmailAndPassword(user.getEmailAddress(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Profile Saved You Can Now Login", Toast.LENGTH_LONG).show();
                            user.setId(task.getResult().getUser().getUid());
                            mDatabase.child("users").child(user.getId()).setValue(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ProfileActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            Log.d("user", "onComplete: " + task.getException());
                        }
                        mProgress.dismiss();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
    }

    public void checkUserName() {
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    userNames.add(user.getUserName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //SAVE URI FROM GALLERY
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {

            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());

                bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            //SAVE URI FROM CAMERA
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);

        }


    }



}
