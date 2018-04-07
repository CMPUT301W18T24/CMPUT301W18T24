package com.example.taskmagic;

/**
 * Created by steve on 2018-03-18.
 */

    import android.app.AlertDialog;
    import android.app.Fragment;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;

public class User_Profile_Frag extends Fragment {
        private EditText fullName;
        private EditText userName;
        private EditText emailAddress;
        private EditText password;
        private EditText phoneNumber;
        private FireBaseManager fmanager;
        private ImageView imageView;
        private Button save;
        private String userid;
        private UserSingleton singleton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_profile_frag,container,false);

        singleton = UserSingleton.getInstance();
        fmanager = new FireBaseManager(singleton.getmAuth(), getActivity());
        listener(singleton.getUserId());
        fullName=(EditText)view.findViewById(R.id.editTextfullName);
        emailAddress=(EditText)view.findViewById(R.id.editTextEmail);
        phoneNumber=(EditText)view.findViewById(R.id.EditTextphoneNumber);
        password=(EditText)view.findViewById(R.id.EditTextpassword);
        userName=(EditText)view.findViewById(R.id.editTextUserName);
        save=(Button)view.findViewById(R.id.buttonSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user=new User(fullName.getText().toString(),emailAddress.getText().toString(),
                        userName.getText().toString(),password.getText().toString(),phoneNumber.getText().toString());
                user.setId(userid);
                fmanager.saveProfile(user);
            }
        });
        return view;
    }
    private void listener(final String requestor) {

        fmanager.getUserInfo(requestor, new OnGetUserInfoListener() {
            @Override
            public void onSuccess(User user) {
                userid=user.getId();
                UserSingleton singleton = UserSingleton.getInstance();
                singleton.setUserName(user.getUserName());
                fullName.setText(user.getFullName());
                emailAddress.setText(user.getEmailAddress());
                phoneNumber.setText(user.getPhoneNumber());
                password.setText(user.getPassword());
                userName.setText(user.getUserName());
            }

            @Override
            public void onFailure(String message) {

            }
        });

    }
}