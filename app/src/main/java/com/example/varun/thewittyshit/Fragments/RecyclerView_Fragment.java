package com.example.varun.thewittyshit.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.example.varun.thewittyshit.Adapters.FirebaseRecylerAdapter;
import com.example.varun.thewittyshit.R;
import com.example.varun.thewittyshit.Utils;
import com.example.varun.thewittyshit.Witt;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class RecyclerView_Fragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    //MyRecycleViewAdapter recyclerViewAdapter;
    static String Arg;
    FirebaseRecylerAdapter adapter;
    private static final int ANIM_DURATION_FRAGMENT = 700;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        String url = "https://thewittyshit.firebaseio.com/Witt/Content";
        final Firebase ref = new Firebase(url);

        Query q = ref.orderByChild("owner_name").equalTo(Arg);

        adapter = new FirebaseRecylerAdapter(Witt.class, R.layout.card_layout,
                FirebaseRecylerAdapter.ContentViewHolder.class, q,getActivity());

        startIntroAnimation();
        itemAnimator();
        adapterAnimation();
        return rootView;
    }


    public static RecyclerView_Fragment newInstance(String username) {
        RecyclerView_Fragment fragment = new RecyclerView_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        Arg = username;
        return fragment;
    }

    private void itemAnimator() {
        SlideInUpAnimator animator = new SlideInUpAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        animator.setAddDuration(1000);
        animator.setRemoveDuration(500);
        recyclerView.setItemAnimator(animator);
    }

    private void adapterAnimation() {
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(this.adapter);
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
}
