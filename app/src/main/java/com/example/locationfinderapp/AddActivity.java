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

    private Button save_button;
    private Button get_address;
    private EditText input_latitude;
    private EditText input_longitude;
    private TextView address_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        save_button = findViewById(R.id.save_button);
        get_address = findViewById(R.id.get_address);

        input_latitude = findViewById(R.id.edit_latitude);
        input_longitude = findViewById(R.id.edit_longitude);

        address_view = findViewById(R.id.address);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        get_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {getAddress(input_latitude,input_longitude);}
        });
    }

    private void getAddress(EditText latitude, EditText longitude) {
        Geocoder gc = new Geocoder(AddActivity.this, Locale.getDefault());

        Double lat = Double.parseDouble(latitude.getText().toString());
        Double lon = Double.parseDouble(longitude.getText().toString());


        try {
            List<Address> list_address = gc.getFromLocation(lat,lon, 1);
            if(list_address.size() > 0){
                //Toast.makeText(AddActivity.this, list_address.get(0).getCountryName(), Toast.LENGTH_SHORT).show();
                address_view.setText(list_address.get(0).getCountryName());
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}