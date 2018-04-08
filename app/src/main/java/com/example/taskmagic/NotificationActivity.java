package com.example.taskmagic;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * Created by hyusuf on 2018-03-11.
 */

public class NotificationActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private FireBaseManager fManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        final UserSingleton singleton = UserSingleton.getInstance();
        auth = singleton.getmAuth();
        recyclerView = (RecyclerView) findViewById(R.id.NotificationsView);
        fManager = new FireBaseManager(auth, getApplicationContext());
        notificationListener(auth.getUid());


    }

    public void notificationListener(final String requestor) {
        fManager.retrieveNotifications(requestor, new OnGetNotificationsListener() {
            @Override
            public void onSuccess(List<NotificationMSG> nList) {
                if (nList.size()>0) {
                    Log.d("List", "onSuccess: "+nList.get(0));
                    updateView(nList);
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void updateView(List<NotificationMSG> nList){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter=new NotificationAdapter(nList,getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
