/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */
package com.example.taskmagic;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yipu on 12/03/2018.
 */

public class BidDialog extends Dialog {
    private Context context;
    private onDialogListener listener;
    private UserTask task;

    // This interface sends the amount value back
    public interface onDialogListener {
        void onEnsure(String amount);
        void onCancel();
    }

    /**
     * This constructor creates a dialogue and displays the details of a UserTask
     * @param context
     * @param task
     * @param listener
     */
    public BidDialog(@NonNull Context context,UserTask task, onDialogListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.task = task;
    }

    EditText amount;
    TextView title;
    TextView description;
    Button confirmButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle SaveInstanceState) {
        super.onCreate(SaveInstanceState);
        setContentView(R.layout.bid_dialog);
        initView();
        setListener();
    }

    /**
     * This method initializes the dialogue and sets the task details into the TextViews
     */
    private void initView() {
        title = findViewById(R.id.textView_titleContent);
        description = findViewById(R.id.textView_descriptionContent);
        amount = findViewById(R.id.editText_amount);
        confirmButton = findViewById(R.id.button_confirm);
        cancelButton = findViewById(R.id.button_cancel);

        title.setText(task.getTitle());
        description.setText(task.getDescription());
    }

    /**
     * This method has two Buttons:
     *      Clicking the confirm button gets the amount value from user input and reset editText
     *      Clicking the cancel button resets the amount value
     *      Both buttons dismisses dialogue
     *      https://stackoverflow.com/questions/25118599/edittext-currency-validation-in-android
     */
    private void setListener() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountStr = amount.getText().toString().trim();
                String pattern = "^([0-9]{1,4})(.[0-9]{0,2})?$"; // 4 digits followe by . followed by 2 digits
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(amountStr);
                if (m.matches()) {
                    dismiss();
                    if(!amountStr.isEmpty()) {
                        listener.onEnsure(amountStr);
                    }
                    amount.setText("");
                } else {
                    amount.setError("Invalid input!");
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
                dismiss();
                listener.onCancel();
            }
        });
    }
}

