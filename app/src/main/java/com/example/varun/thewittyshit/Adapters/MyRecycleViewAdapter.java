package com.example.varun.thewittyshit.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.varun.thewittyshit.R;
import com.example.varun.thewittyshit.Witt;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Varun on 4/19/2016.
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {

    private List<Map<String,?>> mDataSet;
    private Context context;
    public static final String API_KEY = "AIzaSyBr05XlPDCnn0w8JAv4dgo-rTt2vwQoUwc";
    FragmentActivity act;
    OnItemClickListener mItemClickListner;

    public MyRecycleViewAdapter(FragmentActivity activity, List<Map<String, ?>> contentList) {
        if(contentList.size()>0)
        mDataSet = contentList;

        context= activity.getApplicationContext();
        act = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recyclerView;
        Log.i("4", "4");
        switch (viewType){
            case 1:
                recyclerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
                break;
            case 2:
                recyclerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_video, parent, false);
                break;
            default:
                recyclerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
                break;

        }

        ViewHolder vh = new ViewHolder(recyclerView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mDataSet.size()>0) {
            HashMap<String, Witt> temp = (HashMap<String, Witt>) mDataSet.get(position);

            Witt w = new Witt();
            for (String key : temp.keySet()) {
                w = temp.get(key);
                break;
            }
            holder.bindCardData(w);
        }
    }

    public interface OnItemClickListener {
        public void onItemLolClick(View view, int position);
        public void onItemLmaoClick(View view, int position);
        public void onItemRoflClick(View view, int position);
        public void onItemShitClick(View view, int position);
        public void onPopUpMenuClick(View view, int position);
        public void showContent(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener)
    {
        this.mItemClickListner = mItemClickListener;
    }
    @Override
    public int getItemViewType(int position) {

            return 1;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements YouTubePlayer.OnInitializedListener {
        public ImageView uploaderImage;
        public TextView uploaderName;
        public TextView uploaderLocation;
        public ImageView uploadedImage;
       public Button lolButton,roflButton,lmaoButton,turdButton;
        public YouTubePlayerView youTubePlayerView;
        public TextView test;
        Witt video = new Witt();
        YouTubePlayerSupportFragment frag;
        public TextView lolCount;
        public TextView lmaoCount;
        public TextView roflCount;
        public TextView shitCount;
        public ImageView vpopUp;
        public ImageButton AdapterButtonImage;


        public ViewHolder(View itemView) {
            super(itemView);
            uploaderImage=(ImageView)itemView.findViewById(R.id.uploader_image);
            uploaderName=(TextView)itemView.findViewById(R.id.uploader_name);
            uploaderLocation= (TextView) itemView.findViewById(R.id.uploader_location);
            uploadedImage= (ImageView) itemView.findViewById(R.id.uploaded_image);
            lolButton=(Button) itemView.findViewById(R.id.lolButton);
            roflButton=(Button) itemView.findViewById(R.id.roflButton);
            lmaoButton=(Button) itemView.findViewById(R.id.lmaoButton);
            turdButton=(Button) itemView.findViewById(R.id.shitButton);
            vpopUp=(ImageView)itemView.findViewById(R.id.pop_up);
            AdapterButtonImage = (ImageButton)itemView.findViewById(R.id.AdapterButtonImage);

            youTubePlayerView = (YouTubePlayerView)itemView.findViewById(R.id.youtube_player);
            frag =(YouTubePlayerSupportFragment) act.getSupportFragmentManager().findFragmentById(R.id.youtube_player);

            lolCount=(TextView)itemView.findViewById(R.id.LolCount);
            lmaoCount= (TextView) itemView.findViewById(R.id.LmaoCount);
            roflCount=(TextView)itemView.findViewById(R.id.RoflCount);
            shitCount= (TextView) itemView.findViewById(R.id.ShitCount);

            if(AdapterButtonImage != null)
            {
                AdapterButtonImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("NewButton","NewButton1");
                        if(mItemClickListner != null)
                            mItemClickListner.showContent(v,getPosition());
                    }
                });
            }

            if(vpopUp !=null)
            {
                vpopUp.setOnClickListener(new ImageButton.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if((video.getYouTubeURL().equals(""))) {
                            if (mItemClickListner != null) {
                                mItemClickListner.onPopUpMenuClick(v, getPosition());
                            }
                        }
                    }
                });
            }

            if(lolButton != null){
            lolButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListner != null) {
                        mItemClickListner.onItemLolClick(v, getPosition());
                    }
                }
            });}
            if(lmaoButton != null){
            lmaoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListner != null) {
                        mItemClickListner.onItemLmaoClick(v, getPosition());
                    }
                }
            });}
            if(roflButton != null){
            roflButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListner != null) {
                        mItemClickListner.onItemRoflClick(v, getPosition());
                    }
                }
            });}
            if(turdButton != null){
            turdButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListner != null) {
                        mItemClickListner.onItemShitClick(v, getPosition());
                    }
                }
            });}

        }

        public void bindCardData(Witt witt) {
            video = witt;
            uploaderName.setText(witt.getOwner_name());

            if(witt.getYouTubeURL().equals("")) {
                String base64Image = (String) witt.getContentImage();
                byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                if (uploadedImage != null) {
                    uploadedImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                }
                AdapterButtonImage.setVisibility(View.INVISIBLE);
            }
            else
            {
                String img = "http://img.youtube.com/vi/" +witt.getYouTubeURL() +"/0.jpg";
                Picasso.with(context).load(img).into(uploadedImage);
                Drawable img1 = context.getResources().getDrawable( R.drawable.lol );
                AdapterButtonImage.setImageResource(R.drawable.youtube);
            }
            //contentViewHolder.uploadedImage.setImageResource(R.drawable.cardimage);
            uploaderLocation.setText(witt.getPlace());

            if (witt.getOwner_image() != "")
                Picasso.with(context).load(witt.getOwner_image()).into(uploaderImage);
            else
                uploaderImage.setImageResource(R.drawable.blank);

            if(lolCount != null)
            lolCount.setText( witt.getlol().toString());
            if(lmaoCount != null)
            lmaoCount.setText(witt.getLmao().toString());
            if(roflCount != null)
            roflCount.setText( witt.getRofl().toString());
            if(shitCount != null)
            shitCount.setText(witt.getShit().toString());

            /*if(frag != null) {
                frag.initialize(API_KEY, this);
            }*/

        }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
            youTubePlayer.setPlaybackEventListener(playbackEventListener);

            /** Start buffering **/
            if (!b) {
                youTubePlayer.cueVideo(video.getYouTubeURL());
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
}
