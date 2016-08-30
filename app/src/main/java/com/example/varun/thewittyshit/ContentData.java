package com.example.varun.thewittyshit;

import android.content.Context;
import android.util.Log;

import com.example.varun.thewittyshit.Adapters.FirebaseRecylerAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentData {

	public  static String FIREBASE_SERVER =  "https://thewittyshit.firebaseio.com/Witt/Content";
	Firebase mRef;
	FirebaseRecylerAdapter myRecyclerViewAdapter;
	Context mContext;
    List<Map<String,?>> contentList;

	public Firebase getFirebaseRef()
	{
		return mRef;
	}

    public List<Map<String, ?>> getcontentList() {
        return contentList;
    }

    public int getSize(){
        return contentList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < contentList.size()){
            return (HashMap) contentList.get(i);
        } else return null;
    }

	public void addItem(int position, HashMap<String,?> witt_content )
	{
		contentList.add(position, witt_content);
	}


	public void removeItem(int position)
	{
		contentList.remove(position);
	}

	public void removeAllItem(){
		contentList.clear();
	}

	public void removeList(ArrayList<Integer> arr)
	{
		int i=0;
		for(i=0;i<arr.size();i++)
		{
			contentList.remove(arr.get(i));
		}
	}

	public int findwitt_content(String query){
		int  i;
		String test;
		for(i=0;i<contentList.size();i++)
		{
			test = (String)contentList.get(i).get("name");
			if(test.contains(query) || test.toLowerCase().contains(query) || test.toUpperCase().contains(query) ){
				return i;
			}
		}
		return 0;
	}

    public ContentData(){
        contentList = new ArrayList<Map<String,?>>();
		FirebaseRecylerAdapter myRecyclerViewAdapter = null;
		mRef = new Firebase(FIREBASE_SERVER);
	}

	public void populateDate(){
		//contentList.clear();
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

					//Log.i("owner_name",owner_name);

					w.setOwner_name(owner_name);
					String youTubeURL=(String)populate.child("youTubeURL").getValue();
					w.setYouTubeURL(youTubeURL);
					String Type=(String)populate.child("Type").getValue();
					w.setType(Type);
					long date_created=(long)populate.child("date_created").getValue();
					w.setDate_created(date_created);
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
				Log.i("Size PD :", String.valueOf(getcontentList().size()));
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

			}
		});
	}


	public void initializeDataFromCloud()
	{
		contentList.clear();
		mRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
			HashMap<String,String> witt_content = (HashMap<String,String>)dataSnapshot.getValue();
				onItemAddedToCloud(witt_content);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {
				HashMap<String,String> witt_content = (HashMap<String,String>)dataSnapshot.getValue();
				onItemChangedInCloud(witt_content);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				HashMap<String,String> witt_content = (HashMap<String,String>)dataSnapshot.getValue();
				onItemRemovedFromCloud(witt_content);
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {

			}
		});
	}

	public void onItemChangedInCloud(HashMap<String,String> item)
	{
		String id = (String)item.get("id");
		for(int i=0; i<contentList.size() ; i++)
		{
			String mid = (String)contentList.get(i).get("id");
			if(mid.equals(id))
			{
				contentList.remove(i);
				contentList.add(i,item);
				if(myRecyclerViewAdapter != null)
					myRecyclerViewAdapter.notifyItemChanged(i);

				break;
			}
		}
	}

	public void onItemAddedToCloud(HashMap<String,String> item)
	{
		String id = (String)item.get("id");
		int insertPosition = 0;
		for(int i=0; i < contentList.size() ; i++)
		{
			String mid = (String)contentList.get(i).get("id");
			if(mid.equals(id))
				return;
			if(mid.compareTo(id)<0)
				insertPosition=i+1;
			else
				break;
		}
		contentList.add(insertPosition,item);
		if(myRecyclerViewAdapter != null)
			myRecyclerViewAdapter.notifyItemInserted(insertPosition);
	}

	public void onItemRemovedFromCloud(HashMap<String,String> item)
	{
		int position = -1;
		String id = (String)item.get("id");
		for(int i=0 ; i< contentList.size();i++)
		{
			String mid = (String)contentList.get(i).get("id");
			if(mid.equals(id))
			{
				position=i;
				break;
			}
		}
		if(position != -1)
		{
			contentList.remove(position);
			if(myRecyclerViewAdapter != null)
				myRecyclerViewAdapter.notifyItemRemoved(position);
		}
	}
}
