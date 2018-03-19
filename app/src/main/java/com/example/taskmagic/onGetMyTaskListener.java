package com.example.taskmagic;

/**
 * Created by hyusuf on 2018-03-11.
 */

public interface  onGetMyTaskListener {
    public void onSuccess(TaskList taskList);
    public void onFailure(String message);
}
