package com.example.taskmagic;

import java.util.ArrayList;

/**
 * Created by Yipu on 22/02/2018.
 */

class TaskList {
    private ArrayList<UserTask> tasks = new ArrayList<UserTask>();

    public void add(UserTask task) { tasks.add(task); }

    public void remove(UserTask task) {
        tasks.remove(task);
    }

    public Boolean hasTask(UserTask task) {
        return tasks.contains(task);
    }

    public UserTask getTask(int index) {
        if (index < tasks.size()) {
            return tasks.get(index);
        }
        else {
            return null;
        }
    }

    public ArrayList<UserTask> getTaskList() {
        return tasks;
    }

    public int getCount() {
        return tasks.size();
    }
}
