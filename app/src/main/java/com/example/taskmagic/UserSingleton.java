package com.example.taskmagic;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hyusuf on 2018-03-10.
 */

public class UserSingleton {
    private final static UserSingleton obj=new UserSingleton();
    private FirebaseAuth mAuth;
    private String userName;
    private UserSingleton(){}

    public static UserSingleton getInstance(){
        return obj;
    }
    public void setAuth(FirebaseAuth auth){
        this.mAuth=auth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FirebaseAuth getmAuth(){
        return mAuth;
    }
    public String getUserId(){
        return mAuth.getCurrentUser().getUid();
    }
    public void logout(){
        mAuth.signOut();
    }

}
