package com.example.varun.thewittyshit.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.example.varun.thewittyshit.Fragments.RecyclerView_Fragment_Abusive;
import com.example.varun.thewittyshit.Fragments.RecyclerView_Fragment_Funny;
import com.example.varun.thewittyshit.Fragments.RecyclerView_Fragment_Home;
import com.example.varun.thewittyshit.Fragments.RecyclerView_Fragment_Music;
import com.example.varun.thewittyshit.Fragments.RecyclerView_Fragment_Political;
import com.example.varun.thewittyshit.Fragments.RecyclerView_Fragment_Popular;
import com.example.varun.thewittyshit.Fragments.RecyclerView_Fragment_Sports;
import com.example.varun.thewittyshit.R;
import com.example.varun.thewittyshit.Utils;
import com.example.varun.thewittyshit.Witt;
import com.firebase.client.Firebase;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Locale;

public class Activity_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        RecyclerView_Fragment_Sports.ContentClickListner,
        RecyclerView_Fragment_Political.ContentClickListner,
        RecyclerView_Fragment_Popular.ContentClickListner,
        RecyclerView_Fragment_Music.ContentClickListner,
        RecyclerView_Fragment_Abusive.ContentClickListner,
        RecyclerView_Fragment_Funny.ContentClickListner{

    HomeFragmentStatePageAdapter hPagerAdapter;
    ViewPager hViewPager;
    Intent intent;
    Fragment hFragment;
    DrawerLayout drawerLayout;
    Toolbar toolbarBottom,toolBar;
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_PIC = 3;
    TextView textTargetUri;
    ImageView targetImage;
    View mLayout;
    Firebase ref = new Firebase("https://thewittyshit.firebaseio.com/Witt");
    Context mContext;
    String userName;
    String userImage;
    FloatingActionMenu fab;
    private static final int ANIM_DURATION_TOOLBAR = 500;
    private static final int ANIM_DURATION_FAB = 600;

    Fragment frag = null;

    String[] categoryString = {"Sports", "Political", "Popular", "Music", "Abusive", "Funny"};


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY_REQUEST_CODE);
            } else {
                Snackbar.make(mLayout, "Can't open gallery.",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            } else {
                Snackbar.make(mLayout, "Can't add product.",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_home);

        toolBar = (Toolbar) findViewById(R.id.toolbarHome);
        setSupportActionBar(toolBar);

        TextView toolbarTitle = null;
        for (int i = 0; i < toolBar.getChildCount(); ++i) {
            View child = toolBar.getChildAt(i);


            if (child instanceof TextView) {
                toolbarTitle = (TextView)child;
                break;
            }
        }

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/MJ AlGhifari Demo.ttf");
        assert toolbarTitle != null;
        toolbarTitle.setTypeface(custom_font);
        userName = getIntent().getExtras().getString("userName");
        userImage = getIntent().getExtras().getString("userImage");


        toolbarBottom = (Toolbar) findViewById(R.id.toolBarBottom);
        Button homeButtonBottom = (Button) findViewById(R.id.homeButtonBottom);
        assert homeButtonBottom != null;
        homeButtonBottom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(Activity_Home.this, Activity_Home.class);
                activityChangeIntent.putExtra("userName", userName);
                activityChangeIntent.putExtra("userImage", userImage);
                Activity_Home.this.startActivity(activityChangeIntent);
            }
        });





        Button profileButtonBottom = (Button) findViewById(R.id.profileButtonBottom);
        assert profileButtonBottom != null;
        profileButtonBottom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(Activity_Home.this, Activity_Profile_Main.class);
                activityChangeIntent.putExtra("userName", userName);
                activityChangeIntent.putExtra("userImage", userImage);
                Activity_Home.this.startActivity(activityChangeIntent);
            }
        });


        Button infoButtonBottom = (Button) findViewById(R.id.infoButtonBottom);
        assert infoButtonBottom != null;
        infoButtonBottom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(Activity_Home.this, Activity_About_Us.class);
                activityChangeIntent.putExtra("userName", userName);
                activityChangeIntent.putExtra("userImage", userImage);
                Activity_Home.this.startActivity(activityChangeIntent);
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


        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerHome);

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
        drawerLayout.setDrawerListener(toggler);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggler.syncState();


        hPagerAdapter = new HomeFragmentStatePageAdapter(this.getSupportFragmentManager(), 6);

        hViewPager = (ViewPager) findViewById(R.id.pager);
        assert hViewPager != null;
        hViewPager.setAdapter(hPagerAdapter);
        hViewPager.setOffscreenPageLimit(1);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(hViewPager);
        hViewPager.setPageTransformer(true, new CubeOutTransformer());
        mContext = this.getApplicationContext();


        mLayout = findViewById(R.id.addContentMain);
        fab = (FloatingActionMenu) findViewById(R.id.fab);
        assert fab != null;
        FloatingActionButton camera = (FloatingActionButton) fab.getChildAt(0);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                CAMERA_REQUEST_CODE);
                    } else {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    }
                } else {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
            }
        });

        FloatingActionButton gallery = (FloatingActionButton) fab.getChildAt(1);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                GALLERY_REQUEST_CODE);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GALLERY_REQUEST_CODE);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, GALLERY_REQUEST_CODE);
                }

            }
        });
        FloatingActionButton youTubeLink = (FloatingActionButton) fab.getChildAt(2);
        youTubeLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Home.this);
                Dialog d = builder.show();
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.youtube_url, null);
                final EditText input = (EditText) dialogView.findViewById(R.id.edit1);
                builder.setView(dialogView);
                builder.setTitle("Enter youtube video url");
                builder.setMessage("Enter: ");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        Witt w = new Witt();
                        w.setYouTubeURL(value);
                        w.setCategory("");
                        w.setType("Video");
                        String postId ="";
                        Intent intent = new Intent(getApplicationContext(), AddScreen.class);
                        intent.putExtra("postid", postId);
                        intent.putExtra("userName", userName);
                        intent.putExtra("userImage", userImage);
                        intent.putExtra("type","Video");
                        intent.putExtra("WittObj",w);
                        startActivity(intent);
                        return;
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                AlertDialog b = builder.create();
                b.show();
            }

        });

        startIntroAnimation();

    }

    private void customViewPager() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

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
                hFragment = RecyclerView_Fragment_Home.newInstance("Sports");
                getSupportFragmentManager().beginTransaction().replace(R.id.homeContainer, hFragment).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (data != null) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Witt w = new Witt();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                assert bitmap != null;
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] bytes = baos.toByteArray();
                String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                w.setContentImage(base64Image);
                w.setType("Image");
                String postId ="";
                Intent intent = new Intent(this, AddScreen.class);
                intent.putExtra("postid", postId);
                intent.putExtra("userName", userName);
                intent.putExtra("userImage", userImage);
                intent.putExtra("type","Image");
                intent.putExtra("WittObj",w);
                startActivity(intent);
            }
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                Uri targetUri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Witt w = new Witt();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                assert bitmap != null;
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] bytes = baos.toByteArray();
                String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                w.setContentImage(base64Image);
                w.setType("Image");
                Intent intent = new Intent(this, AddScreen.class);
                String postId ="";
                intent.putExtra("postid", postId);
                intent.putExtra("userName", userName);
                intent.putExtra("userImage", userImage);
                intent.putExtra("type","Image");
                intent.putExtra("WittObj",w);
                startActivity(intent);
            }
        }

    }

    @Override
    public void showContentOnClick(View view, Witt w) {
        Intent intent = new Intent(this, YoutubeDialog.class);
        intent.putExtra("url",w.getYouTubeURL());
        startActivity(intent);
    }



    class HomeFragmentStatePageAdapter extends FragmentStatePagerAdapter {
        int count;
        private FragmentManager fm;

        public HomeFragmentStatePageAdapter(FragmentManager fragmentAdapter, int size) {
            super(fragmentAdapter);
            count = size;
            this.fm = fragmentAdapter;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Fragment getItem(int position) {
            int p = hViewPager.getCurrentItem();
            switch(position) {
                case 0:
                    return RecyclerView_Fragment_Sports.newInstance();
                case 1:
                    return RecyclerView_Fragment_Political.newInstance();
                case 2:
                    return RecyclerView_Fragment_Popular.newInstance();
                case 3:
                    return RecyclerView_Fragment_Music.newInstance();
                case 4:
                    return RecyclerView_Fragment_Abusive.newInstance();
                case 5:
                    return RecyclerView_Fragment_Funny.newInstance();
                default:
                    return RecyclerView_Fragment_Sports.newInstance();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            String name = categoryString[position];
            return name.toUpperCase(l);
        }
    }

    private void startIntroAnimation() {
        fab.setTranslationY(2 * 300);

        int actionbarSize = Utils.dpToPx(500);
        toolBar.setTranslationX(-actionbarSize);
        toolbarBottom.setTranslationX(-actionbarSize);

        toolBar.animate()
                .translationX(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300).start();

        toolbarBottom.animate()
                .translationX(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300).start();

        fab.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_FAB)
                .setStartDelay(400).start();
    }
}