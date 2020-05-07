package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.week10.R;

import java.util.ArrayList;

public class UserReservationActivity extends AppCompatActivity {

    Context context;
    ArrayList<UserReservation> userReservationsArray = new ArrayList<>(); //TODO list :D
    Button homebutton8, goProfile;
    RecyclerView rwReservations;
    RecyclerView.Adapter rwAdapter;
    RecyclerView.LayoutManager rwLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation);

        context = UserReservationActivity.this;
        homebutton8 = (Button) findViewById(R.id.homebutton8);
        goProfile = (Button) findViewById(R.id.goProfile);

        userReservationsArray = ReadAndWriteXML.readXML(context);

        rwReservations = (RecyclerView) findViewById(R.id.rwReservations);
        rwReservations.setHasFixedSize(true);
        rwLayoutManager = new LinearLayoutManager(this);
        rwAdapter = new UserReservationAdapter(userReservationsArray);

        rwReservations.setLayoutManager(rwLayoutManager);
        rwReservations.setAdapter(rwAdapter);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.homebutton8)){
                    startActivity(new Intent(UserReservationActivity.this, MainActivity.class));

                } else if (v == findViewById(R.id.goProfile)){
                    finish();
                    startActivity(new Intent(UserReservationActivity.this, ProfileActivity.class));
                }
            }
        };

        homebutton8.setOnClickListener(listener);
        goProfile.setOnClickListener(listener);
    }
}