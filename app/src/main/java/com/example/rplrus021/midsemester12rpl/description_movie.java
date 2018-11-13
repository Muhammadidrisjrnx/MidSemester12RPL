package com.example.rplrus021.midsemester12rpl;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class description_movie extends AppCompatActivity {
    ImageView imageview_movie;
    TextView textview_judul, textview_description;
    Intent intent;
    Bundle bundle;
    String judul, description, image;
    Button button_trailer;
    FloatingActionButton floatingActionButton;
    database_favorite database;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_movie);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        imageview_movie = (ImageView) findViewById(R.id.imageview_movie);
        textview_judul = (TextView) findViewById(R.id.textview_judul);
        textview_description = (TextView) findViewById(R.id.textview_description);
        button_trailer = (Button) findViewById(R.id.button_trailer);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_favorite);
        database = new database_favorite(this);

        textview_description.setMovementMethod(new ScrollingMovementMethod());
        intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            image = bundle.getString("gambar");
            judul = bundle.getString("judul");
            description = bundle.getString("description");
            textview_judul.setText(judul);
            textview_description.setText(description);
            Glide.with(description_movie.this)
                    .load(image)
                    .into(imageview_movie);
        }
        button_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String youtube = "https://www.youtube.com/watch?v=70JIXsey2iI";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube));

                startActivity(browserIntent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = database.insertData(judul, description, image);

            }
        });
    }
}
