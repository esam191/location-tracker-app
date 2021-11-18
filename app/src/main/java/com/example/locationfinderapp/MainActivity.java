package com.example.locationfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LocationDatabase db;
    String t;
    FloatingActionButton add_button;
    Button search;
    EditText search_edit;
    ArrayList<String> loc_id, loc_addr, loc_lat, loc_lon;
    public boolean DATA_REQUESTED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new LocationDatabase(this);
        search_edit = findViewById(R.id.editSearch);
        search = findViewById(R.id.search_button);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t = search_edit.getText().toString();
                //Toast.makeText(MainActivity.this, t, Toast.LENGTH_SHORT).show();
                DATA_REQUESTED = true;
                displayLocation(t);
            }
        });

        db = new LocationDatabase(MainActivity.this);
        loc_id = new ArrayList<>();
        loc_addr = new ArrayList<>();
        loc_lat = new ArrayList<>();
        loc_lon = new ArrayList<>();

        if(!DATA_REQUESTED) displayLocation(t);


    }

    public void displayLocation(String t) {
        Cursor cursor;
        if(DATA_REQUESTED){
            cursor = db.getSearchData(t);
        } else {
            cursor = db.getData();
        }
        loc_id.clear();
        loc_addr.clear();
        loc_lat.clear();
        loc_lon.clear();
        while (cursor.moveToNext()){
            loc_id.add(cursor.getString(0));
            loc_addr.add(cursor.getString(1));
            loc_lat.add(cursor.getString(2));
            loc_lon.add(cursor.getString(3));
        }
        RecyclerView recyclerView = findViewById(R.id.rec_view);
        LocationAdapter a = new LocationAdapter(MainActivity.this,this,loc_id,loc_addr, loc_lat, loc_lon);
        recyclerView.setAdapter(a);
        LinearLayoutManager l = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(l);
    }
}
