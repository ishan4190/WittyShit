package com.example.varun.thewittyshit.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.varun.thewittyshit.R;
import com.example.varun.thewittyshit.Witt;
import com.firebase.client.Firebase;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class addButton extends AppCompatActivity {
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    TextView textTargetUri;
    ImageView targetImage;
    View mLayout;
    Firebase ref = new Firebase("https://thewittyshit.firebaseio.com/Witt");
    Context mContext;

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
        }
        else if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
            else {
                Snackbar.make(mLayout, "Can't add product.",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_button);

        mContext = this.getApplicationContext();


        mLayout = findViewById(R.id.addContentMain);
        FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        FloatingActionButton camera = (FloatingActionButton) fab.getChildAt(0);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                CAMERA_REQUEST_CODE);
                    }else{
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    }
                }else {
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
                }else{
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
                Log.i("DialogActivity", "1");
                AlertDialog.Builder builder = new AlertDialog.Builder(addButton.this);
                builder.setTitle("Enter youtube video url");
                //builder.setMessage("What is your name:");
                builder.setMessage("");
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.youtube_url, null);
                final EditText input = (EditText)dialogView.findViewById(R.id.edit1);
                builder.setView(dialogView);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        Log.d("DialogActivity", "User name: " + value);
                        Firebase newChild = ref.child("Content");
                        // Write data to the location
                        Firebase jobchild = ref.child("Content");
                        Firebase newPostRef = jobchild.push();
                        Witt w = new Witt();
                        w.setYouTubeURL(value);
                        w.setCategory("");
                        w.setType("video");
                        newPostRef.setValue(w);
                        String postId = newPostRef.getKey();
                        Log.i("Post id", postId);
                        Intent intent = new Intent(getApplicationContext(), AddScreen.class);
                        intent.putExtra("postid", postId);
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
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Register activity", "Register activity");
        if (requestCode == CAMERA_REQUEST_CODE) {
            if(data!=null) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Firebase newChild = ref.child("Content");
                // Write data to the location
                Firebase jobchild = ref.child("Content");
                Firebase newPostRef = jobchild.push();
                Witt w = new Witt();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bytes = baos.toByteArray();
                String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                // we finally have our base64 string version of the image, save it.
                w.setContentImage(base64Image);
                w.setType("Image");
                newPostRef.setValue(w);
                String postId = newPostRef.getKey();
                Log.i("Post id", postId);
                Intent intent = new Intent(this, AddScreen.class);
                intent.putExtra("postid", postId);
                startActivity(intent);
            }
        }
        else  if (requestCode == GALLERY_REQUEST_CODE) {
            if(data!=null) {
                Uri targetUri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Firebase newChild = ref.child("Content");
                // Write data to the location
                Firebase jobchild = ref.child("Content");
                Firebase newPostRef = jobchild.push();
                Witt w = new Witt();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bytes = baos.toByteArray();
                String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                // we finally have our base64 string version of the image, save it.
                w.setContentImage(base64Image);
                w.setType("Image");
                newPostRef.setValue(w);
                String postId = newPostRef.getKey();
                Log.i("Post id", postId);

                Intent intent = new Intent(this, AddScreen.class);
                intent.putExtra("postid", postId);
                startActivity(intent);
            }
        }
    }




}
