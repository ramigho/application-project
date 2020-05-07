package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.week10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class ReserveActivity extends AppCompatActivity implements Serializable {

    Context context;
    Button homebutton7, submitReservation;
    TextView resHall, resDatetime, resName, resSecond, goBack;
    EditText resInfo;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        this.context = getApplicationContext();
        homebutton7 = (Button) findViewById(R.id.homebutton7);
        submitReservation = (Button) findViewById(R.id.submitReservation);
        resHall = (TextView) findViewById(R.id.resHall);
        resDatetime = (TextView) findViewById(R.id.resDatetime);
        resName = (TextView) findViewById(R.id.resName);
        resSecond = (TextView) findViewById(R.id.resSecond);
        goBack = (TextView) findViewById(R.id.goBack);
        resInfo = (EditText) findViewById(R.id.resInfo);

        Intent intent = getIntent();
        final String time = intent.getExtras().getString("time");
        final String date = intent.getExtras().getString("date");
        final String hall = intent.getExtras().getString("hall");


        /* Get values from user and reservation itself. */
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("NYT OLLAAN RESERVE ACTIVITYSSÄ:DD");
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                resHall.setText(hall);
                resDatetime.setText(date + "  " +time);
                resName.setText(userProfile.getUserName());
                resSecond.setText(userProfile.getUserSecond());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Databasesta luku epäonnistui.");
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.homebutton7)){
                    finish();
                    startActivity(new Intent(ReserveActivity.this, MainActivity.class));

                } else if (v == findViewById(R.id.submitReservation)){
                    String info = resInfo.getText().toString();

                    Intent reserveIntent = new Intent(context, UserReservationActivity.class);

                    UserReservation userReservation = new UserReservation(firebaseAuth.getUid(), hall, date, time, info);
                    ReadAndWriteXML.writeXML(context, userReservation);

                    finish();
                    startActivity(reserveIntent);

                } else if (v == findViewById(R.id.goBack)){
                    finish();
                    startActivity(new Intent(ReserveActivity.this, MainActivity.class));
                }
            }
        };

        homebutton7.setOnClickListener(listener);
        submitReservation.setOnClickListener(listener);
        goBack.setOnClickListener(listener);
    }
}
