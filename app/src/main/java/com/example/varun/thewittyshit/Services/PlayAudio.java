package com.example.varun.thewittyshit.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.varun.thewittyshit.R;

public class PlayAudio extends Service{
    private static final String LOGCAT = null;
    MediaPlayer objPlayer;

    public void onCreate(){
        super.onCreate();
        Log.d(LOGCAT, "Service Started!");
        objPlayer = MediaPlayer.create(this, R.raw.sleepaway);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        objPlayer.start();
        Log.d(LOGCAT, "Media Player started!");
        if(objPlayer.isLooping() != true){
            Log.d(LOGCAT, "Problem in Playing Audio");
        }
        return 1;
    }

    public void onStop(){
        objPlayer.stop();
        objPlayer.release();
        stopService(new Intent(this, PlayAudio.class));
    }

    public void onPause(){
        objPlayer.stop();
        objPlayer.release();
        stopService(new Intent(this, PlayAudio.class));
    }

    public void onDestroy(){
        objPlayer.stop();
        objPlayer.release();
        super.onDestroy();
        stopService(new Intent(this, PlayAudio.class));

    }

    @Override
    public IBinder onBind(Intent objIndent) {
        return null;
    }
}