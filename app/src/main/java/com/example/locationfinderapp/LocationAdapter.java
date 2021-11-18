package com.example.locationfinderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    private Context context;
    private ArrayList loc_id, loc_addr, loc_lat, loc_lon;
    private int p;
    private Activity activity;
    LocationAdapter( Activity activity, Context context, ArrayList loc_id, ArrayList loc_addr,
                   ArrayList loc_lat, ArrayList loc_lon) {
        this.activity = activity;
        this.context = context;
        this.loc_id = loc_id;
        this.loc_addr = loc_addr;
        this.loc_lat = loc_lat;
        this.loc_lon = loc_lon;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater in = LayoutInflater.from(context);
        View v =  in.inflate(R.layout.location_row, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display_addr.setText(String.valueOf(loc_addr.get(position)));
        holder.display_lat.setText(String.valueOf(loc_lat.get(position)));
        holder.display_lon.setText(String.valueOf(loc_lon.get(position)));

        holder.row_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = holder.getAdapterPosition();
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("id", String.valueOf(loc_id.get(p)));
                intent.putExtra("addr", String.valueOf(loc_addr.get(p)));
                intent.putExtra("lat", String.valueOf(loc_lat.get(p)));
                intent.putExtra("lon", String.valueOf(loc_lon.get(p)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return loc_addr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView display_addr, display_lat, display_lon;
        LinearLayout row_item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            display_addr = itemView.findViewById(R.id.view_add);
            display_lat = itemView.findViewById(R.id.view_lat);
            display_lon= itemView.findViewById(R.id.view_lon);
            row_item = itemView.findViewById(R.id.linear_layout);
        }
    }
}
