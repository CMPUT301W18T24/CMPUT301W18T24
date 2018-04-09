package com.example.taskmagic;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hyusuf on 2018-03-08.
 */


public class FireBaseManager implements OnGetMyTaskListener,OnGetUserInfoListener,OnGetAllTaskReqListener,OnGetATaskListener,OnGetBidsListListener,OnGetAssignedTaskListener,OnGetChatMessagesListener {
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private String taskTag = "task";
    private String userTag = "users";
    private String bidTag = "bids";
    private String messageTag = "messages";
    private Context context;
    private String notificationTag = "Notifications";
    private FirebaseAuth.AuthStateListener listener;


    /**
     * Constructor creates a manager that handles upload and download to database
     *
     * @param user
     * @param context
     */
    public FireBaseManager(FirebaseAuth user, Context context) {
        this.mAuth = user;
        this.database = FirebaseDatabase.getInstance().getReference();
        this.context = context;
        database.keepSynced(true);


    }

    @Override
    public void onSuccess(TaskList taskList) {
    }

    @Override
    public void onSuccess(User user) {

    }

    @Override
    public void onSuccess(UserTask task) {

    }

    @Override
    public void onSuccess(BidList Bids) {

    }

    @Override
    public void onSuccess(ArrayList<ChatMessage> messageList) {

    }


    @Override
    public void onFailure(String message) {

    }


    /**
     * successfully adds a Task to FireBase Database under user login
     *
     * @param task
     */
    public void addTask(UserTask task) {
        DatabaseReference mRef = database.child(taskTag);
        String taskId = mRef.push().getKey();
        task.setId(taskId);
        mRef.child(taskId).setValue(task).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Succesfully added Task", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @param task
     */
    public void editTask(UserTask task) {
        database.child(taskTag).child(task.getId()).setValue(task).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Succesfully edited Task", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * This function is used to remove a task from the database
     * successfully removes a Task to FireBase Database under user login
     *
     * @param taskId
     */
    public void removeTask(String taskId) {
        database.child(taskTag).child(taskId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Succesfully removed task", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * this function can be used to edit profile
     *
     * @param user
     */
    public void saveProfile(User user) {
        database.child(userTag).child(user.getId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void T) {
                //Do whatever
                Toast.makeText(context, "Profile Successfully Saved", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * retrieves user info from database
     *
     * @param userid
     * @param listener
     */
    public void getUserInfo(final String userid, final OnGetUserInfoListener listener) {
        database.child(userTag).child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                listener.onSuccess(user);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toString());
            }
        });
    }

    /**
     * This function asychronously retrieves logged in user Tasks from the Database
     *
     * @param requestor
     * @param listener
     */
    public void getMyTaskData(final String requestor, final OnGetMyTaskListener listener) {
        database.child(taskTag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TaskList taskList = new TaskList();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserTask task = ds.getValue(UserTask.class);
                    if (task.getRequester().equals(requestor) && (task.getStatus().equals("Assigned") || task.getStatus().equals("Requested")
                            || task.getStatus().equals("Done") || task.getStatus().equals("Bidded"))) {
                        //continue;
                        taskList.add(task);
                    }
                    //else if(task.getRequester().equals(requestor)){
                    else {
                        continue;
                        //taskList.add(task);
                    }
                }
                listener.onSuccess(taskList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toString());
            }
        });
    }

    /**
     * this function successfully adds a bid to the database
     *
     * @param bid
     */

    public void addBid(final Bid bid) {
        database.child(bidTag).child(bid.getTaskID() + bid.getProvider()).setValue(bid).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Bid Successfully Saved", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * @param bid
     */
    public void editBid(final Bid bid) {
        database.child(bidTag).child(bid.getTaskID() + bid.getProvider()).setValue(bid).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Succesfully edited Bid", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * this function retrieves all requested task in the database for home Feed
     *
     * @param requestor
     * @param listener
     */
    public void getRequestedTasks(final String requestor, final OnGetAllTaskReqListener listener) {
        database.child(taskTag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TaskList taskList = new TaskList();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserTask task = ds.getValue(UserTask.class);
                    Log.d("get requested", "onDataChange: " + task.getId());
                    if (task.getRequester().equals(requestor) || task.getStatus().equals("Done") || task.getStatus().equals("Assigned")) {
                        continue;
                    } else {
                        taskList.add(task);
                    }
                }
                Log.d("List Created", "yoo");
                listener.onSuccess(taskList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toString());
            }
        });
    }

    /**
     * This function gets the users task information
     *
     * @param taskId
     * @param listener
     */
    public void getTaskInfo(final String taskId, final OnGetATaskListener listener) {
        database.child(taskTag).child(taskId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserTask task = dataSnapshot.getValue(UserTask.class);
                listener.onSuccess(task);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toString());
            }
        });
    }

    /**
     * This function get the bids of a given user in the database
     *
     * @param provider
     * @param listener
     */
    public void getBidsList(final String provider, final OnGetBidsListListener listener) {
        final BidList bidList = new BidList();
        database.child(bidTag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bid bid = ds.getValue(Bid.class);
                    if (bid.getProvider().equals(provider)) {
                        bidList.add(bid);
                    }

                }
                listener.onSuccess(bidList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toString());
            }
        });
    }

    /**
     * This function gets the users logged in assigned tasks from the database
     *
     * @param provider
     * @param listener
     */
    public void getAssignedTasks(final String provider, final OnGetAssignedTaskListener listener) {
        final TaskList taskList = new TaskList();
        database.child(taskTag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserTask task = ds.getValue(UserTask.class);
                    if (task.getProvider().equals(provider) && task.getStatus().equals("Assigned")) {
                        taskList.add(task);
                    }

                }
                listener.onSuccess(taskList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toString());
            }
        });
    }

    public void addChatMessage(ChatMessage message) {
        database.child(messageTag).child(message.getSenderId()).push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(context, "Error " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        database.child(messageTag).child(message.getReceiverId()).push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(context, "Error " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void retrieveChatMessages(final String sender, final String receiver, final OnGetChatMessagesListener listener) {
        database.child(messageTag).child(sender).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ChatMessage> chatList = new ArrayList<ChatMessage>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    ChatMessage message = snap.getValue(ChatMessage.class);
                    if (message.getSenderId().equals(sender) && message.getReceiverId().equals(receiver) || message.getSenderId().equals(receiver) && message.getReceiverId().equals(sender)) {
                        chatList.add(message);
                    }

                }
                listener.onSuccess(chatList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toString());
            }
        });
    }

    /**
     * This function get the bids for a given task in the database
     *
     * @param taskId
     * @param listener
     */
    public void getBidsListOnTask(final String taskId, final OnGetBidsListListener listener) {
        database.child(bidTag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BidList bidList = new BidList();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bid bid = ds.getValue(Bid.class);
                    if (bid.getTaskID().equals(taskId)) {
                        bidList.add(bid);
                    }
                }
                listener.onSuccess(bidList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toString());
            }
        });

    }

    /**
     * This function get the lowest bid for a given task in the database
     *
     * @param taskId
     * @param listener
     */
    public void getLowestBidOnTask(final String taskId, final OnGetLowestBidListener listener) {
        database.child(bidTag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float lowestBid = 99999f;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bid bid = ds.getValue(Bid.class);
                    if (bid.getTaskID().equals(taskId) && (float) bid.getAmount() < lowestBid) {
                        lowestBid = (float) bid.getAmount();
                    }
                }
                listener.onSuccess(lowestBid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toString());
            }
        });
    }

    /**
     * This function is used to remove a Bid from the database
     * successfully removes a Bid to FireBase Database under user login
     *
     * @param bid
     */
    public void removeBid(Bid bid) {
        database.child(bidTag).child(bid.getTaskID() + bid.getProvider()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Succesfully removed Bid", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * This function takes a Location and return a Tasklist containing all tasks that has a distance less
     * than 5km to the Location
     * @param position
     * @param listener
     */
    public void searchViaGeoOnTask(final Location position, final OnGetTaskLsitGeoListener listener) {
        database.child(taskTag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TaskList taskList = new TaskList();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    UserTask task = ds.getValue(UserTask.class);
                    try {
                        if (task.getStatus().equals("Bidded") || task.getStatus().equals("Requested")) {
                            Location taskLocation = new Location("");
                            taskLocation.setLatitude(task.getLatitude());
                            taskLocation.setLongitude(task.getLongtitude());
                            if (position.distanceTo(taskLocation) <= 5000f) {
                                taskList.add(task);
                            }
                        }
                    } catch (Exception e) {}

                }
                listener.onSuccess(taskList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

