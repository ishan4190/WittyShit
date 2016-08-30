package com.example.varun.thewittyshit.Activities;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.varun.thewittyshit.R;
import com.example.varun.thewittyshit.Witt;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddScreen extends AppCompatActivity implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    //http://youtu.be/<VIDEO_ID>
    public static final String VIDEO_ID = "Tni74ocFxtQ";
    public static final String API_KEY = "AIzaSyBr05XlPDCnn0w8JAv4dgo-rTt2vwQoUwc";
    TextView textTargetUri;
    ImageView targetImage;
    Firebase ref = new Firebase("https://thewittyshit.firebaseio.com/Witt");

    Witt w = new Witt();
    String key;
    String username;
    String userimage;
    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    Button btnFusedLocation;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    private List<Address> addresses;
    String cityName;
    String lat;
    String lng;
    String category;
    String type_of_content;
    Fragment mFragment;
    Context mContext;
    Switch loc;
    Switch content;
    Toolbar toolBar;
    ActionBar addActionBar;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //key = getIntent().getExtras().getString("postid");
        username = getIntent().getExtras().getString("userName");
        userimage = getIntent().getExtras().getString("userImage");
        type_of_content = getIntent().getExtras().getString("type");
        w= (Witt)getIntent().getSerializableExtra("WittObj");

        setContentView(R.layout.add_screen_fragment);
        Firebase.setAndroidContext(this);
        mContext = getApplication().getApplicationContext();
        toolBar = (Toolbar) findViewById(R.id.toolbarAddScreen);
        setSupportActionBar(toolBar);
        addActionBar=getSupportActionBar();

        addActionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Button submit =(Button)findViewById(R.id.butt);



        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Sports", "Political", "Popular","Music","Abusive","Funny"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        category = "Popular";
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.i("item", (String) parent.getItemAtPosition(position));
                category = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        createLocationRequest();
        buildGoogleApiClient();

        loc = (Switch)findViewById(R.id.location);

        content = (Switch)findViewById(R.id.content);

        content.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ImageView img = (ImageView) findViewById(R.id.test);
                if(isChecked)
                {
                String base64Image = (String) w.getContentImage();
                if (base64Image != null) {
                    byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                    if (img != null)
                        img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                    }
                }
                else
                    img.setImageResource(R.drawable.explorepanda);
            }
        });

        if(submit!=null)
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText desc = (EditText) findViewById(R.id.TextEnter);
                if (desc != null) {
                    String description = desc.getText().toString();
                    w.setDescription(description);
                    desc.setText("");
                }

                final EditText tag = (EditText) findViewById(R.id.Tag);
                if (tag != null) {
                    String _tag = tag.getText().toString();
                    w.setTags(_tag);
                }

                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                Log.i("Date", currentDateTimeString);
                Date d = new Date();
                w.setDate_created(d.getTime() * (-1));

                w.setOwner_name(username);
                w.setOwner_image(userimage);

                if (loc.isChecked()) {
                    w.setLongitude(lng);
                    w.setLattitude(lat);
                    w.setPlace(cityName);
                }
                w.setCategory(category);

               String url = w.getYouTubeURL();
                if(url != "")
                {
                    String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

                    Pattern compiledPattern = Pattern.compile(pattern);
                    Matcher matcher = compiledPattern.matcher(url);

                    if(matcher.find()){
                        w.setYouTubeURL(matcher.group());
                    }
                }
                try {
                    Firebase jobchild = ref.child("Content");
                    Firebase newPostRef = jobchild.push();
                    newPostRef.setValue(w);
                    key = newPostRef.getKey();
                    Toast.makeText(getApplicationContext(), "Content Posted Successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Failed to post", Toast.LENGTH_SHORT).show();
                }

                /*HashMap<String,Witt> x = new HashMap<String, Witt>();
                x.put(key, w);
                PopulateDB.contentList.add(x);
                Toast.makeText(getApplicationContext(), "Content Posted Successfully", Toast.LENGTH_SHORT).show();*/

                Intent intent = new Intent(AddScreen.this, Activity_Profile_Main.class);
                intent.putExtra("userName", username);
                intent.putExtra("userImage", userimage);
                startActivity(intent);
            }
        });



        Button location = (Button)findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            updateUI();
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
        Log.d(TAG, "onStart fired2 ..............");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
    }

    private void updateUI() {
        Log.d(TAG, "UI update initiated .............");
        if (null != mCurrentLocation) {
            getCityName();
            lat = String.valueOf(mCurrentLocation.getLatitude());
            lng = String.valueOf(mCurrentLocation.getLongitude());
        } else {
            Log.d(TAG, "location is null ...............");
        }
    }
    private void getCityName() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(String.valueOf(mCurrentLocation.getLatitude())), Double.parseDouble(String.valueOf(mCurrentLocation.getLongitude())), 1);
        } catch (IOException e) {
            Log.d("Exception", "Exception while getting location...");
            e.printStackTrace();
        }

        cityName = addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }

}

