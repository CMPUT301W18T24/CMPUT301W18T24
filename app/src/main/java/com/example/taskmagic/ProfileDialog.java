/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yipu on 07/04/2018.
 */

public class ProfileDialog extends Dialog {
    private Context context;
    private User user;
    private ProfileDialog.onDialogListener listener;

    public interface onDialogListener {
        void onEnsure();
    }


    /**
     * This constructor creates a dialogue and displays the details of a User
     * @param context
     * @param user
     */
    public ProfileDialog(@NonNull Context context, User user, onDialogListener listener) {
        super(context);
        this.context = context;
        this.user = user;
        this.listener = listener;
    }

    TextView username;
    TextView email;
    TextView phone;
    TextView fullname;
    Button backButton;
    ImageView contactButton;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle SaveInstanceState) {
        super.onCreate(SaveInstanceState);
        setContentView(R.layout.profile_dialog);
        initView();
        setListener();
    }

    /**
     * This method initializes the dialogue and sets the task details into the TextViews
     */
    private void initView() {
        username = (TextView) findViewById(R.id.textView_usernameContent);
        fullname = (TextView) findViewById(R.id.textView_fullnameContent);
        email = (TextView) findViewById(R.id.textView_emailContent);
        phone = (TextView) findViewById(R.id.textView_phoneContent);
        contactButton = (ImageView) findViewById(R.id.imageButton);
        backButton = (Button) findViewById(R.id.button_back) ;
        ratingBar = (RatingBar) findViewById(R.id.ratingBar) ;

        username.setText(user.getUserName());
        fullname.setText(user.getFullName());
        email.setText(user.getEmailAddress());
        phone.setText(user.getPhoneNumber());

    }

    /**
     * This method has two Buttons:
     *      Clicking the confirm button gets the amount value from user input and reset editText
     *      Clicking the cancel button resets the amount value
     *      Both buttons dismisses dialogue
     *      https://stackoverflow.com/questions/25118599/edittext-currency-validation-in-android
     */
    private void setListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEnsure();
                Toast.makeText(getContext(),"Messeging.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
