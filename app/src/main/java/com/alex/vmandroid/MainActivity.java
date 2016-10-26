package com.alex.vmandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alex.vmandroid.entities.User;
import com.alex.utils.EncapsulateParseJson;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AudioRecordDemo().getNoiseLevel();

        String json = "{\"UserName\":\"user\",\"Password\":\"password\"}";

        User user = EncapsulateParseJson.parse(User.class, json);

        if (user != null) {
            Log.i(TAG, "onCreate: " + user.getPassword());
        } else {
            Log.i(TAG, "onCreate: user is null");
        }

        user.setPassword("123456");
        user.setUserName("i am name");


        Log.i(TAG, "onCreate: " + EncapsulateParseJson.encapsulate(user));

    }
}
