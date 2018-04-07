package com.example.taskmagic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.*;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MainActivity extends AppCompatActivity {
    private EditText mEmail;
    private EditText mPassword;
    private String email;
    private String password;
    private Button mLogin;
    private FirebaseAuth mAuth;
    private TextView register;
    private DatabaseReference datbase;
    private ProgressDialog progress;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private UserSingleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmail=(EditText)findViewById(R.id.editTextEmail);
        mPassword=(EditText)findViewById(R.id.editTextpassword);
        mEmail.setText("HPaderan@gmail.com");
        mPassword.setText("123456Aa");
        mLogin=(Button) findViewById(R.id.buttonlogin);
        register=(TextView)findViewById(R.id.textViewNewUser);
        mAuth=FirebaseAuth.getInstance();
        progress=new ProgressDialog(this);
        singleton=UserSingleton.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser()!=null){
                    singleton.setAuth(mAuth);
                    User user=new User();
                    //user.setId("");
                    Intent myIntent = new Intent(MainActivity.this, HomeFeed.class);
                    //myIntent.putExtra("id","gfozdHipBXQP1aaXsb9sQOYMLWW2");
                    startActivity(myIntent);
                    Log.d("Main", "onAuthStateChanged: "+singleton.getmAuth());
                    init();
                }
            }
        };


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=mEmail.getText().toString().trim();
                password=mPassword.getText().toString().trim();
                login(email,password);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });


    }
    private void init(){
        String token= FirebaseInstanceId.getInstance().getToken();
        Log.d("MainActivity", "init: "+token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String Token){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        UserSingleton singleton= UserSingleton.getInstance();
        reference.child("users").child(singleton.getUserId()).child("token").setValue(Token);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.signOut();
        mAuth.addAuthStateListener(mAuthListener);

    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    // This function logs the user into the app
    public void login(String email,String password){
        progress.setMessage("Please Wait");
        progress.show();
        Log.d("login", "login: "+ email +password);
        (mAuth.signInWithEmailAndPassword(email,password)).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(),"SignIn Sucessful",Toast.LENGTH_LONG).show();

                }
                else{
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(),"Login Error",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Activity", "onFailure: "+e.getMessage());
            }
        });

    }
}
