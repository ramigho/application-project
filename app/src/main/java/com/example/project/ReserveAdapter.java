package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.week10.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;



public class ReserveAdapter extends RecyclerView.Adapter<ReserveAdapter.ReservationViewHolder> {
    private ArrayList<Reservation> reservationList;
    Context context;

    public static class ReservationViewHolder extends RecyclerView.ViewHolder{
        TextView header;
        TextView subheader;
        TextView clock;
        Button reserve;
        RelativeLayout relativeLayout;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            subheader = itemView.findViewById(R.id.subheader);
            clock = itemView.findViewById(R.id.clock);
            reserve = itemView.findViewById(R.id.reserve);
            relativeLayout = itemView.findViewById(R.id.recyclerView);
        }
    }

    public ReserveAdapter(ArrayList<Reservation> reservations, Context context){
        reservationList = reservations;
        this.context = context;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler, parent, false);
        ReservationViewHolder rvh = new ReservationViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation currentReservation = reservationList.get(position);


        if (reservationList.get(position).getStatus().equals("VARATTU")){
            holder.relativeLayout.setBackgroundColor(Color.RED);
            holder.header.setBackgroundColor(Color.RED);
            holder.header.setText(currentReservation.getStatus());
        }

        holder.clock.setText(currentReservation.getTime());
        holder.header.setText(currentReservation.getStatus());
        holder.subheader.setText(currentReservation.getHall());

        /* Apumuuttujia */
        final String currentClock = currentReservation.getTime();
        final String currentDate = currentReservation.getDate();
        final String chosenHall = currentReservation.getHall();

        holder.reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null){
                        Intent reserveIntent = new Intent(context, ReserveActivity.class);
                        reserveIntent.putExtra("time", currentClock);
                        reserveIntent.putExtra("date", currentDate);
                        reserveIntent.putExtra("hall", chosenHall);
                        reserveIntent.addFlags(reserveIntent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(reserveIntent);
                    } else {
                        Toast.makeText(context, "SINUN TÄYTYY KIRJAUTUA SISÄÄN VARATAKSESI VUORON.", Toast.LENGTH_LONG).show();

                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }
}