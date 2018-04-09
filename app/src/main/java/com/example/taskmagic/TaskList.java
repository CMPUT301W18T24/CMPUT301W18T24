/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import java.util.ArrayList;

/**
 * Created by Yipu on 22/02/2018.
 */

class TaskList {
    private ArrayList<UserTask> tasks = new ArrayList<UserTask>();

    /**
     * This method takes a UserTask as a parameter and adds it to the TaskList
     * @param task
     */
    public void add(UserTask task) { tasks.add(task); }

    /**
     * Takes a UserTask as a parameter and removes task from the TaskList
     * @param task
     */
    public void remove(UserTask task) {
        tasks.remove(task);
    }

    /**
     * This method takes a UserTask as a parameter and checks if UserTask is in the TaskList
     * @param task
     */
    public Boolean hasTask(UserTask task) {
        return tasks.contains(task);
    }

    /**
     * This method takes an int as a parameter and gets the UserTask at specified index
     * @param index
     */
    public UserTask getTask(int index) {
        if (index < tasks.size()) {
            return tasks.get(index);
        }
        else {
            return null;
        }
    }

    /**
     * This method returns the list of UserTasks
     *
     */
    public ArrayList<UserTask> getTaskList() {
        return tasks;
    }

    /**
     * This method returns the size of the TaskList
     */
    public int getCount() {
        return tasks.size();
    }
}
