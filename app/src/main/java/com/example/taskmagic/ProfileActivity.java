package com.example.taskmagic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.*;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hyusuf on 2018-03-08.
 */

public class ProfileActivity extends AppCompatActivity{
    private EditText mUserName;
    private EditText mPassWord;
    private EditText mEmail;
    private EditText mPhoneNumber;
    private EditText mFullName;
    private EditText mPassword;
    private String UserName;
    private String PassWord;
    private String Email;
    private String PhoneNumber;
    private String FullName;
    private Button mRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Boolean registered;
    private ProgressDialog mProgress;

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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mProgress=new ProgressDialog(this);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = mEmail.getText().toString();
                PassWord = mPassword.getText().toString();
                FullName = mFullName.getText().toString();
                PhoneNumber = mPhoneNumber.getText().toString();
                UserName = mUserName.getText().toString();
                User user = new User(FullName, Email, UserName, PassWord, PhoneNumber);
                addUser(user);
            }
        });


    }

    public void addUser(final User user) {
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
                            Toast.makeText(ProfileActivity.this, "Authentication failed."+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            Log.d("user", "onComplete: "+task.getException());
                        }
                        mProgress.dismiss();
                        finish();
                    }
                });
    }

        }
