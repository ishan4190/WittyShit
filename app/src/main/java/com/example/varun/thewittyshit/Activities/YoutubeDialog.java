package com.example.varun.thewittyshit.Activities;

import android.os.Bundle;

import com.example.varun.thewittyshit.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeDialog extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    public static final String API_KEY = "AIzaSyBr05XlPDCnn0w8JAv4dgo-rTt2vwQoUwc";
    public YouTubePlayerFragment youTubePlayerFragment;
    public YouTubePlayerFragment frag;
    public YouTubePlayerView youTubePlayerView;
    public String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        url = getIntent().getExtras().getString("url");
        setContentView(R.layout.activity_youtube_dialog);

       /* frag = (YouTubePlayerSupportFragment) getFragmentManager()
                .findFragmentById(R.id.youtube_player);*/

        //frag =(YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_player);
        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        /** Start buffering **/
        if (!b) {
            youTubePlayer.cueVideo(url);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };
}


