package com.example.taskmagic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class addBidActivity extends AppCompatActivity {
    private TextView titleText;
    private TextView descriptionText;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bid);
        final Task task = (Task) getIntent().getSerializableExtra("task");

        titleText = (TextView) findViewById(R.id.taskTitleContent);
        descriptionText = (TextView) findViewById(R.id.taskDescriptionContent);

        Button confirmButton = (Button) findViewById(R.id.button_confirm);
        Button cancelButton = (Button) findViewById(R.id.button_cancel);


        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());
    }

}
