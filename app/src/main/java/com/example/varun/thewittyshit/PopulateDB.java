package com.example.varun.thewittyshit;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Varun on 4/24/2016.
 */
public class PopulateDB {

    public  static String FIREBASE_SERVER =  "https://thewittyshit.firebaseio.com/Witt/Content";
    Firebase mRef = new Firebase(FIREBASE_SERVER);
    public static List<Map<String,?>> contentList = new ArrayList<Map<String, ?>>();

    public PopulateDB()
    {
        contentList.clear();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot populate : dataSnapshot.getChildren()){
                    Witt w = new Witt();
                    Long rofl = (Long)populate.child("rofl").getValue();
                    w.setRofl(rofl.intValue());
                    Long lol = (Long)populate.child("lol").getValue();
                    w.setlol(lol.intValue());
                    Long lmao = (Long)populate.child("lmao").getValue();
                    w.setLmao(lmao.intValue());
                    Long shit = (Long)populate.child("shit").getValue();
                    w.setshit(shit.intValue());
                    String contentImage=(String)populate.child("contentImage").getValue();
                    w.setContentImage(contentImage);
                    String owner_name=(String)populate.child("owner_name").getValue();
                    w.setOwner_name(owner_name);
                    String youTubeURL=(String)populate.child("youTubeURL").getValue();
                    w.setYouTubeURL(youTubeURL);
                    String Type=(String)populate.child("Type").getValue();
                    w.setType(Type);
                    //long date_created=(long)populate.child("date_created").getValue();
                    w.setDate_created(0);
                    String owner_image=(String)populate.child("owner_image").getValue();
                    w.setOwner_image(owner_image);
                    String category=(String)populate.child("category").getValue();
                    w.setCategory(category);
                    String description=(String)populate.child("description").getValue();
                    w.setDescription(description);
                    String lattitude=(String)populate.child("lattitude").getValue();
                    w.setLattitude(lattitude);
                    String longitude=(String)populate.child("longitude").getValue();
                    w.setLongitude(longitude);
                    String place=(String)populate.child("place").getValue();
                    w.setPlace(place);

                    String key = populate.getKey();

                    HashMap<String,Witt> content = new HashMap<String, Witt>();

                    content.put(key, w);
                    contentList.add(content);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


}
