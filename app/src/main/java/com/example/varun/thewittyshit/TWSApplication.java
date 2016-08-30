package com.example.varun.thewittyshit;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Varun on 4/13/2016.
 */
public class TWSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        // other setup code
    }
}
