package com.example.locationfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {
    String id, display_addr, display_lat, display_lon;
    EditText lat, lon;
    TextView addr;
    Button delButton, updButton, get_address;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        LocationDatabase db = new LocationDatabase(UpdateActivity.this);

        delButton = findViewById(R.id.delete_button);
        updButton = findViewById(R.id.update_button);
        get_address = findViewById(R.id.get_address);
        addr = findViewById(R.id.address);
        lat = findViewById(R.id.edit_latitude);
        lon = findViewById(R.id.edit_longitude);

        id = getIntent().getStringExtra("id");
        display_addr = getIntent().getStringExtra("addr");
        display_lat = getIntent().getStringExtra("lat");
        display_lon = getIntent().getStringExtra("lon");

        addr.setText(display_addr);
        lat.setText(display_lat);
        lon.setText(display_lon);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteLocation(id);
                Intent intent = new Intent(UpdateActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        updButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display_addr = addr.getText().toString().trim();
                display_lat = lat.getText().toString().trim();
                display_lon = lon.getText().toString().trim();
                db.updateLocation(id,display_addr,display_lat,display_lon);

                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        get_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //converting to double
                Double lati = Double.parseDouble(lat.getText().toString());
                Double longi = Double.parseDouble(lon.getText().toString());
                address = getAddress(lati,longi);
                addr.setText(address);
            }
        });
    }
    private String getAddress(double latitude, double longitude) {
        Geocoder gc = new Geocoder(UpdateActivity.this, Locale.getDefault());
        try {
            List<Address> list_address = gc.getFromLocation(latitude,longitude, 1);
            if(list_address.size() > 0){
                address = list_address.get(0).getAddressLine(0);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return address;
    }
}