package com.example.taskmagic;

/**
 * Created by hyusuf on 2018-03-18.
 */

public interface OnGetAssignedTaskListener  {
    public void onSuccess(TaskList tasks);
    public void onFailure(String message);
}
