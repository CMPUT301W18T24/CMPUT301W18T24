/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import java.util.ArrayList;
import java.util.List;

public interface OnGetNotificationsListener {
    public void onSuccess(List<NotificationMSG> nList);
    public void onFailure(String message);
}
