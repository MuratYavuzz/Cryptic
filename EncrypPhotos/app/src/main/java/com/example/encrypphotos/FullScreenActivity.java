package com.example.encrypphotos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class FullScreenActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        imageView = (ImageView) findViewById(R.id.image_view);
        getSupportActionBar().hide();

        Intent i = getIntent();

        int position = i.getExtras().getInt("id");
        File image = (File) getIntent().getExtras().get("fileImages");
        ImageAdapter imageAdapter = new ImageAdapter (this,null,null,null);
        imageView.setImageURI(Uri.fromFile(image));
    }
}
