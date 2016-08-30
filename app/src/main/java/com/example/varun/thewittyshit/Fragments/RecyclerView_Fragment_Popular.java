package com.example.varun.thewittyshit.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.PopupMenu;

import com.example.varun.thewittyshit.Adapters.FirebaseRecylerAdapter;
import com.example.varun.thewittyshit.Adapters.MyRecycleViewAdapter;
import com.example.varun.thewittyshit.ContentData;
import com.example.varun.thewittyshit.PopulateDB;
import com.example.varun.thewittyshit.R;
import com.example.varun.thewittyshit.Utils;
import com.example.varun.thewittyshit.Witt;
import com.firebase.client.Firebase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class RecyclerView_Fragment_Popular extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    MyRecycleViewAdapter recyclerViewAdapter;
    ContentData contentData = new ContentData();
    FirebaseRecylerAdapter adapter;
    private static final int ANIM_DURATION_FRAGMENT = 700;
    private static RecyclerView_Fragment_Popular instance = null;
    ContentClickListner itemClickListner;


    public interface ContentClickListner {
        public void showContentOnClick(View view, Witt w);
    }

    public void setOnItemClickListener(ContentClickListner mItemClickListener)
    {
        this.itemClickListner = mItemClickListener;
    }
    public RecyclerView_Fragment_Popular()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        itemClickListner = (ContentClickListner) getActivity();
        recyclerView.setLayoutManager(layoutManager);

        String url = "https://thewittyshit.firebaseio.com/Witt/Content";
        final Firebase ref = new Firebase(url);

        final List<Map<String,?>> catList = new ArrayList<Map<String, ?>>();
        if(PopulateDB.contentList.size()>0) {
            for (int i = 0; i < PopulateDB.contentList.size(); i++) {
                HashMap<String, Witt> temp = (HashMap<String, Witt>) PopulateDB.contentList.get(i);
                Witt w = new Witt();
                for (String key : temp.keySet()) {
                    w = temp.get(key);
                    break;
                }
                if (w.getCategory().equals("Popular")) {
                    catList.add(temp);
                }
            }
            Collections.reverse(catList);
        }
        Log.i("CatListSize :", String.valueOf(catList.size()));

        //recyclerViewAdapter = new MyRecycleViewAdapter(getActivity(), PopulateDB.contentList);
        recyclerViewAdapter = new MyRecycleViewAdapter(getActivity(), catList);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClickListener(new MyRecycleViewAdapter.OnItemClickListener() {

            @Override
            public void onItemLolClick(View view, int position) {
                HashMap<String, Witt> temp = (HashMap<String, Witt>) catList.get(position);
                Witt t = new Witt();
                String postId = "";
                for (String id : temp.keySet()) {
                    t = (Witt) temp.get(id);
                    postId = id;
                    break;
                }
                Integer lol = t.getlol() + 1;
                t.setlol(lol);
                temp.put(postId, t);
                catList.set(position, temp);

                for (int i = 0; i < PopulateDB.contentList.size(); i++) {
                    HashMap<String, Witt> list_map = (HashMap<String, Witt>) PopulateDB.contentList.get(i);
                    Witt t_list = new Witt();
                    String match_id = "";
                    for (String id : list_map.keySet()) {
                        match_id = id;
                        break;
                    }
                    if (match_id.equals(postId)) {
                        t_list = list_map.get(match_id);
                        t_list.setlol(lol);
                        list_map.put(match_id, t_list);
                        PopulateDB.contentList.set(i, list_map);
                        break;
                    }

                }
                String url = "https://thewittyshit.firebaseio.com/Witt";
                Firebase ref = new Firebase(url);
                ref.child("Content").child(postId).child("lol").setValue(t.getlol());
                recyclerViewAdapter.notifyItemChanged(position);
            }

            @Override
            public void onItemLmaoClick(View view, int position) {
                HashMap<String, Witt> temp = (HashMap<String, Witt>) catList.get(position);
                Witt t = new Witt();
                String postId = "";
                for (String id : temp.keySet()) {
                    t = (Witt) temp.get(id);
                    postId = id;
                    break;
                }
                Integer lmao = t.getLmao() + 1;
                t.setLmao(lmao);
                temp.put(postId, t);
                catList.set(position, temp);

                for (int i = 0; i < PopulateDB.contentList.size(); i++) {
                    HashMap<String, Witt> list_map = (HashMap<String, Witt>) PopulateDB.contentList.get(i);
                    Witt t_list = new Witt();
                    String match_id = "";
                    for (String id : list_map.keySet()) {
                        match_id = id;
                        break;
                    }
                    if (match_id.equals(postId)) {
                        t_list = list_map.get(match_id);
                        t_list.setLmao(lmao);
                        list_map.put(match_id, t_list);
                        PopulateDB.contentList.set(i, list_map);
                        break;
                    }

                }
                String url = "https://thewittyshit.firebaseio.com/Witt";
                Firebase ref = new Firebase(url);
                ref.child("Content").child(postId).child("lmao").setValue(t.getLmao());
                recyclerViewAdapter.notifyItemChanged(position);

            }

            @Override
            public void onItemRoflClick(View view, int position) {
                HashMap<String, Witt> temp = (HashMap<String, Witt>) catList.get(position);
                Witt t = new Witt();
                String postId = "";
                for (String id : temp.keySet()) {
                    t = (Witt) temp.get(id);
                    postId = id;
                    break;
                }
                Integer rofl = t.getRofl() + 1;
                t.setRofl(rofl);
                temp.put(postId, t);
                catList.set(position, temp);

                for (int i = 0; i < PopulateDB.contentList.size(); i++) {
                    HashMap<String, Witt> list_map = (HashMap<String, Witt>) PopulateDB.contentList.get(i);
                    Witt t_list = new Witt();
                    String match_id = "";
                    for (String id : list_map.keySet()) {
                        match_id = id;
                        break;
                    }
                    if (match_id.equals(postId)) {
                        t_list = list_map.get(match_id);
                        t_list.setRofl(rofl);
                        list_map.put(match_id, t_list);
                        PopulateDB.contentList.set(i, list_map);
                        break;
                    }

                }
                String url = "https://thewittyshit.firebaseio.com/Witt";
                Firebase ref = new Firebase(url);
                ref.child("Content").child(postId).child("rofl").setValue(t.getRofl());
                recyclerViewAdapter.notifyItemChanged(position);

            }

            @Override
            public void onItemShitClick(View view, int position) {
                HashMap<String, Witt> temp = (HashMap<String, Witt>) catList.get(position);
                Witt t = new Witt();
                String postId = "";
                for (String id : temp.keySet()) {
                    t = (Witt) temp.get(id);
                    postId = id;
                    break;
                }
                Integer shit = t.getShit() + 1;
                t.setshit(shit);
                temp.put(postId, t);
                catList.set(position, temp);

                for (int i = 0; i < PopulateDB.contentList.size(); i++) {
                    HashMap<String, Witt> list_map = (HashMap<String, Witt>) PopulateDB.contentList.get(i);
                    Witt t_list = new Witt();
                    String match_id = "";
                    for (String id : list_map.keySet()) {
                        match_id = id;
                        break;
                    }
                    if (match_id.equals(postId)) {
                        t_list = list_map.get(match_id);
                        t_list.setshit(shit);
                        list_map.put(match_id, t_list);
                        PopulateDB.contentList.set(i, list_map);
                        break;
                    }

                }
                String url = "https://thewittyshit.firebaseio.com/Witt";
                Firebase ref = new Firebase(url);
                ref.child("Content").child(postId).child("shit").setValue(t.getShit());
                recyclerViewAdapter.notifyItemChanged(position);

            }

            @Override
            public void onPopUpMenuClick(View view, int position) {
                final int pos = position;
                final PopupMenu popUp = new PopupMenu(getActivity(), view);
                popUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.share:
                                MenuItem shareItem = popUp.getMenu().findItem(R.id.share);
                                ShareActionProvider shareActionProvider;
                                shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
                                HashMap<String,Witt> temp = (HashMap<String, Witt>) catList.get(pos);
                                Witt t = new Witt();
                                String postId = "";
                                for(String id : temp.keySet())
                                {
                                    t = (Witt) temp.get(id);
                                    postId = id;
                                    break;
                                }
                                String base64Image = (String) t.getContentImage();
                                byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                                Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                                try {

                                    File cachePath = new File(getContext().getCacheDir(), "images");
                                    cachePath.mkdirs(); // don't forget to make the directory
                                    FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
                                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    stream.close();
                                }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                File imagePath = new File(getContext().getCacheDir(), "images");
                                File newFile = new File(imagePath, "image.png");
                                Uri contentUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), "com.example.varun.thewittyshit.fileprovider", newFile);
                                if (contentUri != null) {

                                    Intent shareIntent = new Intent();
                                    shareIntent.setAction(Intent.ACTION_SEND);
                                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                                    shareIntent.setDataAndType(contentUri, getActivity().getContentResolver().getType(contentUri));
                                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                                    startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                                }
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                MenuInflater inflater = popUp.getMenuInflater();
                inflater.inflate(R.menu.menu_popupcard, popUp.getMenu());
                popUp.show();
            }

            @Override
            public void showContent(View view, int position) {
                HashMap<String,Witt> temp = (HashMap<String, Witt>) catList.get(position);
                Witt t = new Witt();
                String postId = "";
                for(String id : temp.keySet())
                {
                    t = (Witt) temp.get(id);
                    postId = id;
                    break;
                }
                itemClickListner.showContentOnClick(view,t);
            }
        });
        startIntroAnimation();
        itemAnimator();
        adapterAnimation();
        return rootView;
    }

    private void itemAnimator() {
        SlideInUpAnimator animator = new SlideInUpAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        animator.setAddDuration(1000);
        animator.setRemoveDuration(500);
        recyclerView.setItemAnimator(animator);
    }

    private void adapterAnimation() {
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(this.recyclerViewAdapter);
        alphaAdapter.setDuration(500);
        recyclerView.setAdapter(alphaAdapter);
    }
    private void startIntroAnimation() {
        int recyclerViewSize = Utils.dpToPx(2000);
        recyclerView.setTranslationY(+recyclerViewSize);
        recyclerView.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_FRAGMENT)
                .setStartDelay(500).start();

    }
    public static RecyclerView_Fragment_Popular newInstance() {
        if(instance == null) {
            instance = new RecyclerView_Fragment_Popular();
            Bundle args = new Bundle();
            instance.setArguments(args);
            return instance;
        }
        else
            return instance;
    }
}
