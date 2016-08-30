package com.example.varun.thewittyshit.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.varun.thewittyshit.R;
import com.example.varun.thewittyshit.Services.PlayAudio;

public class Splashscreen extends Activity {
    MediaPlayer objPlayer;
    Thread splashTread;
    Thread crackTread;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        TextView splashScreenLogoText = (TextView) findViewById(R.id.logoText);


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/MJ AlGhifari Demo.ttf");
        splashScreenLogoText.setTypeface(custom_font);

        //PopulateDB db = new PopulateDB();

        Runnable visible = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                final ImageView img = (ImageView)findViewById(R.id.crack1);
                if(img!=null)
                    img.post(new Runnable() {
                        @Override
                        public void run() {
                            playAudio(getWindow().getDecorView().getRootView());
                            img.setVisibility(View.VISIBLE);

                        }
                    });
            }
        };
        stopAudio(getWindow().getDecorView().getRootView());
        new Thread(visible).start();
        StartAnimations();
        /*objPlayer = MediaPlayer.create(this,R.raw.breakingscreen);
        objPlayer.start();*/
    }
    public void playAudio(View view) {
        Intent objIntent = new Intent(this, PlayAudio.class);
        startService(objIntent);
    }

    public void stopAudio(View view) {
        Intent objIntent = new Intent(this, PlayAudio.class);
        stopService(objIntent);
    }
    private void StartAnimations() {

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited1 = 0;
                    // Splash screen pause time
                    while (waited1 < 2000) {
                        sleep(100);
                        waited1 += 500;
                    }

                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splashscreen.this,login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();

    }

}
