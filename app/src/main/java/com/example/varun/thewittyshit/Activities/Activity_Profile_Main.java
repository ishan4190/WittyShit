package com.example.varun.thewittyshit.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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

import com.example.varun.thewittyshit.Fragments.RecyclerView_Fragment;
import com.example.varun.thewittyshit.PopulateDB;
import com.example.varun.thewittyshit.R;
import com.example.varun.thewittyshit.Utils;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class Activity_Profile_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment pFragment;
    DrawerLayout drawerLayout;
    Intent intent;
    ProfileFragmentStatePageAdapter pPagerAdapter;
    ViewPager pViewPager;
    Toolbar toolbarBottom,toolBar;
    Context context;
    CollapsingToolbarLayout collapsingToolbar;

    String userName;
    String userImage;
    Context mContext;
    private static final int ANIM_DURATION_TOOLBAR = 500;
    private static final int ANIM_DURATION_FAB = 600;

    public boolean isNetworkAvailable(final Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        PopulateDB temp = new PopulateDB();


        int a = 0;
        for(int i=0; i < 100000000 ; i++)
            a++;

        if(!isNetworkAvailable(getApplicationContext()))
            PopulateDB.contentList.clear();

        userName = getIntent().getExtras().getString("userName");
        userImage = getIntent().getExtras().getString("userImage");
        this.mContext = getApplicationContext();
        ImageView profile_image = (ImageView)findViewById(R.id.uploader_image);
        if(!(userImage.equals("")) && profile_image != null)
            Picasso.with(mContext).load(userImage).into(profile_image);
        else {
            assert profile_image != null;
            profile_image.setImageResource(R.drawable.blank);
        }

        TextView profile_name = (TextView)findViewById(R.id.uploader_name);
        if(profile_name != null) {
            if(!(userName.equals("")))
            profile_name.setText(userName);
            else
                profile_name.setText("Anonymous");
        }


        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Profile");

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/MJ AlGhifari Demo.ttf");
        collapsingToolbar.setCollapsedTitleTypeface(custom_font);
        collapsingToolbar.setExpandedTitleTypeface(custom_font);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.darkOrange));


        loadBackdrop();
        toolbarBottom = (Toolbar) findViewById(R.id.toolBarBottom);

        Button homeButtonBottom = (Button) findViewById(R.id.homeButtonBottom);
        assert homeButtonBottom != null;
        homeButtonBottom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(Activity_Profile_Main.this, Activity_Home.class);

                // currentContext.startActivity(activityChangeIntent);
                activityChangeIntent.putExtra("userName", userName);
                activityChangeIntent.putExtra("userImage", userImage);
                Activity_Profile_Main.this.startActivity(activityChangeIntent);
            }
        });





        Button profileButtonBottom = (Button) findViewById(R.id.profileButtonBottom);
        assert profileButtonBottom != null;
        profileButtonBottom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(Activity_Profile_Main.this, Activity_Profile_Main.class);

                // currentContext.startActivity(activityChangeIntent);
                activityChangeIntent.putExtra("userName", userName);
                activityChangeIntent.putExtra("userImage", userImage);
                Activity_Profile_Main.this.startActivity(activityChangeIntent);
            }
        });


        Button infoButtonBottom = (Button) findViewById(R.id.infoButtonBottom);
        assert infoButtonBottom != null;
        infoButtonBottom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(Activity_Profile_Main.this, Activity_About_Us.class);

                // currentContext.startActivity(activityChangeIntent);
                activityChangeIntent.putExtra("userName", userName);
                activityChangeIntent.putExtra("userImage", userImage);
                Activity_Profile_Main.this.startActivity(activityChangeIntent);
            }
        });



        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        assert navigationView != null;
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.name);
        if(nav_user != null)
            nav_user.setText(userName);

        ImageView nav_image = (ImageView)hView.findViewById(R.id.my_image);
        if(!(userImage.equals("")) && nav_image != null)
            Picasso.with(getApplicationContext()).load(userImage).into(nav_image);
        else {
            assert nav_image != null;
            nav_image.setImageResource(R.drawable.blank);
        }


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null); //  makes tint of icon null thus revealing original color

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerProfile);

        ActionBarDrawerToggle toggler = new ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(toggler);
       // getSupportActionBar().setHomeButtonEnabled(true);
        toggler.syncState();

        pPagerAdapter = new ProfileFragmentStatePageAdapter(getSupportFragmentManager(),1);
        pViewPager = (ViewPager)findViewById(R.id.pager);
        assert pViewPager != null;
        pViewPager.setAdapter(pPagerAdapter);

        TabLayout tabLayout =(TabLayout)findViewById(R.id.tab_layout);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(pViewPager);
        startIntroAnimation();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
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
                pFragment = RecyclerView_Fragment.newInstance(userName);
                getSupportFragmentManager().beginTransaction().replace(R.id.profileContainer, pFragment).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



class ProfileFragmentStatePageAdapter extends FragmentPagerAdapter {

    String[] categoryString = {"POSTS"};
    int count;
    public ProfileFragmentStatePageAdapter(FragmentManager fragmentAdapter, int size)
    {
        super(fragmentAdapter);
        count = size;
    }

    @Override
    public int getCount() {
        return count ;
    }

    @Override
    public Fragment getItem(int position) {
        return RecyclerView_Fragment.newInstance(userName);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        String name = categoryString[position];
        return name.toUpperCase(l);
    }
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

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        // Glide.with(this).load(R.drawable.avengers).centerCrop().into(imageView);
    }
}