package com.example.varun.thewittyshit.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.example.varun.thewittyshit.Adapters.FirebaseRecylerAdapter;
import com.example.varun.thewittyshit.Adapters.MyRecycleViewAdapter;
import com.example.varun.thewittyshit.ContentData;
import com.example.varun.thewittyshit.PopulateDB;
import com.example.varun.thewittyshit.R;
import com.example.varun.thewittyshit.Utils;
import com.example.varun.thewittyshit.Witt;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class RecyclerView_Fragment_Home extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    MyRecycleViewAdapter recyclerViewAdapter;
    ContentData contentData = new ContentData();
    FirebaseRecylerAdapter adapter;
    static String Arg_Category;
    private static final int ANIM_DURATION_FRAGMENT = 700;

    public RecyclerView_Fragment_Home()
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

        recyclerView.setLayoutManager(layoutManager);

        String url = "https://thewittyshit.firebaseio.com/Witt/Content";
        final Firebase ref = new Firebase(url);
        Log.i("Fragment ::", Arg_Category);

        /*adapter = new FirebaseRecylerAdapter(Witt.class, R.layout.card_layout,
                FirebaseRecylerAdapter.ContentViewHolder.class, ref,getActivity());
        recyclerView.setAdapter(adapter);*/
        List<Map<String,?>> catList = new ArrayList<Map<String, ?>>();
        for(int i=0 ; i < PopulateDB.contentList.size(); i++) {
            HashMap<String, Witt> temp = (HashMap<String, Witt>) PopulateDB.contentList.get(i);
            Witt w = new Witt();
            for (String key : temp.keySet()) {
                w = temp.get(key);
                break;
            }
            if(w.getCategory().equals(Arg_Category))
            {
                catList.add(temp);
            }
        }
        Log.i("CatListSize :", String.valueOf(catList.size()));

        //recyclerViewAdapter = new MyRecycleViewAdapter(getActivity(), PopulateDB.contentList);
        recyclerViewAdapter = new MyRecycleViewAdapter(getActivity(), catList);
        recyclerView.setAdapter(recyclerViewAdapter);
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

    public static RecyclerView_Fragment_Home newInstance(String category) {
        RecyclerView_Fragment_Home fragment = new RecyclerView_Fragment_Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        Arg_Category = category;
        return fragment;
    }
}
