package com.example.varun.thewittyshit.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.varun.thewittyshit.R;
import com.example.varun.thewittyshit.Witt;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by Varun on 4/19/2016.
 */
public class FirebaseRecylerAdapter extends FirebaseRecyclerAdapter<Witt,FirebaseRecylerAdapter.ContentViewHolder> {

    private Context mContext ;

    public FirebaseRecylerAdapter(Class<Witt> modelClass, int modelLayout,
                                    Class<ContentViewHolder> holder, Query ref,Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;
          }

    @Override
    protected void populateViewHolder(FirebaseRecylerAdapter.ContentViewHolder contentViewHolder, Witt witt, int i) {
            contentViewHolder.vpopUp.setVisibility(View.INVISIBLE);
            contentViewHolder.uploaderName.setText(witt.getOwner_name());
            if(witt.getYouTubeURL().equals("")) {
            String base64Image = (String) witt.getContentImage();
            byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
            if (contentViewHolder.uploadedImage != null) {
                contentViewHolder.uploadedImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            }
                contentViewHolder.AdapterButtonImage.setVisibility(View.INVISIBLE);
        }
        else
        {
            String img = "http://img.youtube.com/vi/" +witt.getYouTubeURL() +"/0.jpg";
            Picasso.with(mContext).load(img).into(contentViewHolder.uploadedImage);
            contentViewHolder.AdapterButtonImage.setImageResource(R.drawable.youtube);
        }
            contentViewHolder.uploaderLocation.setText(witt.getPlace());
            if (witt.getOwner_image() != "")
                Picasso.with(mContext).load(witt.getOwner_image()).into(contentViewHolder.uploaderImage);
            else
                contentViewHolder.uploaderImage.setImageResource(R.drawable.blank);

        if(contentViewHolder.lolCount != null)
            contentViewHolder.lolCount.setText(witt.getlol().toString());
        if(contentViewHolder.lmaoCount != null)
            contentViewHolder.lmaoCount.setText(witt.getLmao().toString());
        if(contentViewHolder.roflCount != null)
            contentViewHolder.roflCount.setText(witt.getRofl().toString());
        if(contentViewHolder.shitCount != null)
            contentViewHolder.shitCount.setText(witt.getShit().toString());

    }

    //TODO: Populate ViewHolder and add listeners.
    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        public ImageView uploaderImage;
        public TextView uploaderName;
        public TextView uploaderLocation;
        public ImageView uploadedImage;
        public Button lolButton,roflButton,lmaoButton,turdButton;
        public TextView lolCount;
        public TextView lmaoCount;
        public TextView roflCount;
        public TextView shitCount;
        public ImageView vpopUp;
        public ImageButton AdapterButtonImage;

        public ContentViewHolder(View itemView) {
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
            lolCount=(TextView)itemView.findViewById(R.id.LolCount);
            lmaoCount= (TextView) itemView.findViewById(R.id.LmaoCount);
            roflCount=(TextView)itemView.findViewById(R.id.RoflCount);
            shitCount= (TextView) itemView.findViewById(R.id.ShitCount);

        }
    }
}
