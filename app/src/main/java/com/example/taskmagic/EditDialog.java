package com.example.taskmagic;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Yipu on 13/03/2018.
 */

class EditDialog extends Dialog {

    private final Context context;
    private final EditDialog.onDialogListener listener;
    private final UserTask task;

    public interface onDialogListener {
        void onEnsure(String title, String description, String date, ArrayList<Photo> images);
    }

    public EditDialog(@NonNull Context context, UserTask task, EditDialog.onDialogListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.task = task;
    }


}
