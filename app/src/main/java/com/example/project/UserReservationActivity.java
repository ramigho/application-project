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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.ListIterator;

public class UserReservationActivity extends AppCompatActivity {

    Context context;
    ArrayList<UserReservation> parseArray = new ArrayList<>();
    ArrayList<UserReservation> userReservationsArray = new ArrayList<>();
    ArrayList<Integer> toDelete = new ArrayList<>();
    Button homebutton8, goProfile;
    RecyclerView rwReservations;
    RecyclerView.Adapter rwAdapter;
    RecyclerView.LayoutManager rwLayoutManager;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation);

        context = UserReservationActivity.this;
        homebutton8 = (Button) findViewById(R.id.homebutton8);
        goProfile = (Button) findViewById(R.id.goProfile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        // TODO Tiedoston luku
        toDelete.clear();
        parseArray = ReadAndWriteXML.readXML(context);
        for (int i = 0; i<parseArray.size(); i++) {
            String dateStr = parseArray.get(i).getDate();
            System.out.println(dateStr);
            String[] splitDate = dateStr.split("\\.");
            System.out.println(splitDate[0]);
            int dayDate = Integer.parseInt(splitDate[0]);
            int monthDate = Integer.parseInt(splitDate[1]);
            monthDate = monthDate - 1;
            int yearDate = Integer.parseInt(splitDate[2]);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            Date today = c.getTime();
            c.set(Calendar.YEAR, yearDate);
            c.set(Calendar.MONTH, monthDate);
            c.set(Calendar.DAY_OF_MONTH, dayDate);
            Date dateSpecified = c.getTime();

            if (!dateSpecified.before(today)) {
                if (parseArray.get(i).getUserID().equals(firebaseAuth.getUid())) {
                    userReservationsArray.add(parseArray.get(i));
                }
            } else {
                toDelete.add(i);
            }
        }

        deleteXML(toDelete);

        rwReservations = (RecyclerView) findViewById(R.id.rwReservations);
        rwReservations.setHasFixedSize(true);
        rwLayoutManager = new LinearLayoutManager(this);
        rwAdapter = new UserReservationAdapter(userReservationsArray, context);

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

    public void deleteXML(ArrayList<Integer> toDelete){

        for (int i = toDelete.size(); i-- > 0; ) {
            System.out.println(toDelete.get(i));
            ReadAndWriteXML.deleteRes(context, toDelete.get(i));
        }
    }

}
