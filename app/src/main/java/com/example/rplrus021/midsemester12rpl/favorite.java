package com.example.rplrus021.midsemester12rpl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class favorite extends AppCompatActivity {
    database_favorite database;
    RecyclerView recyclerView;
    public ArrayList<data> arrayList;
    TextView textView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = new database_favorite(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view2);
//        textView = (TextView) findViewById(R.id.textview);
        arrayList = new ArrayList<data>();
        if (database.getData() == null) {
            textView.setVisibility(View.VISIBLE);
        } else if (database.getData() != null) {
            arrayList = database.getData();
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter_favorite adapter = new adapter_favorite(getApplicationContext(), arrayList);
            recyclerView.setAdapter(adapter);
            Log.e("database_failed", "onCreate: " + "Success");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
