package com.example.varun.thewittyshit.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.varun.thewittyshit.R;
import com.example.varun.thewittyshit.Utils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

public class Activity_About_Us extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FragmentStatePageAdapter ePagerAdapter;
    ViewPager eViewPager;
    Intent intent;
    Fragment eFragment;
    DrawerLayout drawerLayout;
    Toolbar toolbarBottom,toolBar;
    String userName;
    String userImage;
    private static final int ANIM_DURATION_TOOLBAR = 500;
    private static final int ANIM_DURATION_FAB = 600;
    CollapsingToolbarLayout collapsingToolbar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //  MovieData movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        userName = getIntent().getExtras().getString("userName");
        userImage = getIntent().getExtras().getString("userImage");
        /*intent.putExtra("userName", userName);
        intent.putExtra("userImage", userImage);*/

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("About Us");

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/MJ AlGhifari Demo.ttf");
        collapsingToolbar.setCollapsedTitleTypeface(custom_font);
        collapsingToolbar.setExpandedTitleTypeface(custom_font);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.darkOrange));

        //toolbar.get add new font here
        toolbarBottom = (Toolbar) findViewById(R.id.toolBarBottom);
        TextView description=(TextView) findViewById(R.id.aboutusdescription);
        description.setText("\n" +
                "How often have you been declined and dejected in your life. By people, colleagues, friends and girlfriends. \n" +
                "We at The WittyShit have heard people tell us so a llot of time \"Dude! You are full of shit!\".. Umm! So well\n" +
                "guess what we are. The witty shit lets you post and share and react to the Universal shit. The Shit so cool that \n" +
                "it doesn't need to be sh*t censored. So the next time someone tells you \" You are full of shit\" don't shrug your \n" +
                "shoulders off. Be proud of yourself. Coz probably now you are the coolest, wittiest \"Shitloader\" ;)");

        Button homeButtonBottom = (Button) findViewById(R.id.homeButtonBottom);
        assert homeButtonBottom != null;
        homeButtonBottom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(Activity_About_Us.this, Activity_Home.class);

                // currentContext.startActivity(activityChangeIntent);
                activityChangeIntent.putExtra("userName", userName);
                activityChangeIntent.putExtra("userImage", userImage);
                Activity_About_Us.this.startActivity(activityChangeIntent);
            }
        });





        Button profileButtonBottom = (Button) findViewById(R.id.profileButtonBottom);
        assert profileButtonBottom != null;
        profileButtonBottom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(Activity_About_Us.this, Activity_Profile_Main.class);

                // currentContext.startActivity(activityChangeIntent);
                activityChangeIntent.putExtra("userName", userName);
                activityChangeIntent.putExtra("userImage", userImage);
                Activity_About_Us.this.startActivity(activityChangeIntent);
            }
        });


        Button infoButtonBottom = (Button) findViewById(R.id.infoButtonBottom);
        assert infoButtonBottom != null;
        infoButtonBottom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(Activity_About_Us.this, Activity_About_Us.class);
                // currentContext.startActivity(activityChangeIntent);
                activityChangeIntent.putExtra("userName", userName);
                activityChangeIntent.putExtra("userImage", userImage);
                Activity_About_Us.this.startActivity(activityChangeIntent);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        assert navigationView != null;
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.name);
        if (nav_user != null)
            nav_user.setText(userName);

        ImageView nav_image = (ImageView) hView.findViewById(R.id.my_image);
        if (userImage != "" && nav_image != null)
            Picasso.with(getApplicationContext()).load(userImage).into(nav_image);
        else {
            assert nav_image != null;
            nav_image.setImageResource(R.drawable.blank);
        }
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null); //  makes tint of icon null thus revealing original color

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerAboutUs);

        ActionBarDrawerToggle toggler = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_mail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"varunjindal1991@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Whats your shit?");
                intent.putExtra(Intent.EXTRA_TEXT, "Put down your crap here");
                startActivity(Intent.createChooser(intent, ""));
            }
        });
        drawerLayout.setDrawerListener(toggler);
        //getSupportActionBar().setHomeButtonEnabled(true);
        toggler.syncState();
        startIntroAnimation();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
      /*  if(mFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "recyclerFrag", mFragment);
        }*/
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemHome:
                intent = new Intent(this, Activity_Home.class);
                intent.putExtra("userName", userName);
                intent.putExtra("userImage", userImage);
                startActivity(intent);
                break;


            case R.id.itemProfile:
                intent = new Intent(this, Activity_Profile_Main.class);
                intent.putExtra("userName", userName);
                intent.putExtra("userImage", userImage);
                startActivity(intent);
                break;

            case R.id.itemAboutUs:
                intent = new Intent(this, Activity_About_Us.class);
                intent.putExtra("userName", userName);
                intent.putExtra("userImage", userImage);
                startActivity(intent);
                break;


            default:
                intent = new Intent(this, Activity_About_Us.class);
                intent.putExtra("userName", userName);
                intent.putExtra("userImage", userImage);
                startActivity(intent);
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startIntroAnimation() {


        int collapsingbarSize = Utils.dpToPx(500);
        collapsingToolbar.setTranslationX(-collapsingbarSize);
        toolbarBottom.setTranslationX(-collapsingbarSize);



        collapsingToolbar.animate()
                .translationX(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300).start();

        toolbarBottom.animate()
                .translationX(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300).start();




    }
}