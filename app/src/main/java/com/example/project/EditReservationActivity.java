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

public class EditReservationActivity extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4, returnBack;
    EditText editInfo;
    Button editReservation, deleteRes, homebutton9;
    EditText resInfo;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservation);

        this.context = getApplicationContext();
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        returnBack = (TextView) findViewById(R.id.returnBack);
        editInfo = (EditText) findViewById(R.id.editInfo);
        editReservation = (Button) findViewById(R.id.editReservation);
        deleteRes = (Button) findViewById(R.id.deleteRes);
        homebutton9 = (Button) findViewById(R.id.homebutton9);

        Intent intent = getIntent();
        final String time = intent.getExtras().getString("time");
        final String date = intent.getExtras().getString("date");
        final String hall = intent.getExtras().getString("hall");
        final int positionvalue = intent.getExtras().getInt("position");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("NYT OLLAAN RESERVE ACTIVITYSSÄ:DD");
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                tv1.setText(hall);
                tv2.setText(date + "  " +time);
                tv3.setText(userProfile.getUserName());
                tv4.setText(userProfile.getUserSecond());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Databasesta luku epäonnistui.");
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.homebutton9)){
                    finish();
                    startActivity(new Intent(EditReservationActivity.this, MainActivity.class));

                } else if (v == findViewById(R.id.deleteRes)){
                    ReadAndWriteXML.deleteRes(context, positionvalue);
                    System.out.println("POISTETTU");
                    finish();
                    startActivity(new Intent(EditReservationActivity.this, UserReservationActivity.class));

                } else if (v == findViewById(R.id.editReservation)){
                    String info = resInfo.getText().toString();
                    ReadAndWriteXML.deleteRes(context, positionvalue);
                    UserReservation userReservation = new UserReservation(firebaseAuth.getUid(), hall, date, time, info);
                    ReadAndWriteXML.writeXML(context, userReservation);
                    finish();
                    startActivity(new Intent(EditReservationActivity.this, UserReservationActivity.class));
                }
            }
        };
        homebutton9.setOnClickListener(listener);
        deleteRes.setOnClickListener(listener);
        editReservation.setOnClickListener(listener);
    }
}