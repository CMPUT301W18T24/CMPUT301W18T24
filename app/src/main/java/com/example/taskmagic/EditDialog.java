package com.example.taskmagic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by yuandi on 2018-03-11.
 */

public class EditDialog extends Dialog {
    private Context context;
    private onDialogListener listener;
    private UserTask task;

    // This interface returns Title, Date, and Description back
    public interface onDialogListener{
        void onEnsure(String title, String date, String description);
    }

    /**
     * This constructor creates the EditDialogue and sets date and details of Task
     * @param context
     * @param task
     * @param listener
     */
    public EditDialog(@NonNull Context context, UserTask task, onDialogListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.task = task;
    }


    protected  void onCreate(Bundle SaveInstanceState){
        super.onCreate(SaveInstanceState);
        setContentView(R.layout.activity_edit_task);
        initView();
        setListener();
    }
    EditText titleText;
    EditText dateText;
    EditText descriptionText;

    Button confirmButton;
    Button cancelButton;

    /**
     * This method sets Title, Date, and Description into EditTexts
     */
    private void initView(){
        titleText = findViewById(R.id.editText_titleContent);
        titleText.setText(task.getTitle());

        dateText = findViewById(R.id.editText_dateContent);
        dateText.setText(task.getDate());

        descriptionText = findViewById(R.id.editText_descriptionContent);
        descriptionText.setText(task.getDescription());

        confirmButton = findViewById(R.id.button_edit1);
        cancelButton = findViewById(R.id.button_edit2);

    }

    /**
     * This method calls checkInput to confirm valid inputs; sends an error if user input is invalid
     */
    private void setListener() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String title = titleText.getText().toString().trim();
                String date = dateText.getText().toString().trim();
                String description = descriptionText.getText().toString().trim();
                //Photo image = task.getPhoto();

                if (checkInput(title, date, description)) {
                    dismiss();
                    listener.onEnsure(title, date, description);
                } else {
                    if (title.length() == 0) {
                        titleText.setError("Non empty!");
                    } else if (title.length() > 20) {
                        titleText.setError("Title too long!");
                    } else if (!date.matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")) {
                        dateText.setError("Formate: yyy-mm-dd");
                    } else if (description.length() == 0) {
                        descriptionText.setError("Non empty!");
                    }
                }
            }
            //Intent intent = new Intent(context, HomeFeed.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //context.;
            //go back to homeFeed
            //https://stackoverflow.com/questions/14059810/go-back-to-mainactivity-when-ok-pressed-in-alertdialog-in-android

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    /**
     * Checks all inputs in respective fields and returns a Boolean
     * @param title
     * @param dateString
     * @param descriptionString
     * @return
     */
    private boolean checkInput(String title, String dateString, String descriptionString){
        if (title.length() == 0 || title.length()>20 || !dateString.matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$") || descriptionString.length() == 0){
            return false;
        }
        else {
            return true;
        }
    }

}
