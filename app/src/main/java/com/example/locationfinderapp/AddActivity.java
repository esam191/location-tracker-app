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
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    //declaring variables
    LocationDatabase db;
    private Button save_button, get_address;
    private EditText input_latitude, input_longitude;
    private TextView address_view;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = new LocationDatabase(this);

        save_button = findViewById(R.id.save_button);
        get_address = findViewById(R.id.get_address);

        input_latitude = findViewById(R.id.edit_latitude);
        input_longitude = findViewById(R.id.edit_longitude);

        address_view = findViewById(R.id.address);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addLocation(address,input_latitude.getText().toString(),
                        input_longitude.getText().toString());
                //db.addLocation(address,String.valueOf(lat),String.valueOf(lon));

                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        get_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //converting to double
                Double lat = Double.parseDouble(input_latitude.getText().toString());
                Double lon = Double.parseDouble(input_longitude.getText().toString());
                address = getAddress(lat,lon);
                address_view.setText(address);
            }
        });
    }

    private String getAddress(double latitude, double longitude) {
        Geocoder gc = new Geocoder(AddActivity.this, Locale.getDefault());
        try {
            List<Address> list_address = gc.getFromLocation(latitude,longitude, 1);
            if(list_address.size() > 0){
                //Toast.makeText(AddActivity.this, list_address.get(0).getCountryName(),Toast.LENGTH_SHORT).show();
                address = list_address.get(0).getAddressLine(0);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return address;
    }

    private void genRandomData(){
        //Math.floor(Math.random()*(max-min+1)+min)
        int i = 0;
        while(i < 50) {
            float random_lat = (float) Math.floor(Math.random() * (90 - (-90 + 1) + (-90)));
            float random_long = (float) Math.floor(Math.random() * (80 - (-180 + 1) + (-180)));

            String random_address = getAddress(random_lat,random_long);
            i++;
            db.addLocation(random_address, Float.toString(random_lat), Float.toString(random_long));
        }
    }
}