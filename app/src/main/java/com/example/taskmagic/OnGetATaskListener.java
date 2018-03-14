package com.example.taskmagic;

/**
 * Created by hyusuf on 2018-03-14.
 */

public interface OnGetATaskListener {
    public void onSuccess(UserTask task);
    public void onFailure(String message);
}
