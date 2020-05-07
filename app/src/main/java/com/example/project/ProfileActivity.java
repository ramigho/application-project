package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.week10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvSecond, tvPhone, tvAdress, tvTown, tvPost;
    Button editProfile;
    Button homebutton6;
    Button editLog;
    Button uReservations;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = (TextView) findViewById(R.id.tvName);
        tvSecond = (TextView) findViewById(R.id.tvSecond);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvAdress = (TextView) findViewById(R.id.tvAdress);
        tvTown = (TextView) findViewById(R.id.tvTown);
        tvPost = (TextView) findViewById(R.id.tvPost);
        editProfile = (Button) findViewById(R.id.editProfile);
        homebutton6 = (Button) findViewById(R.id.homebutton6);
        editLog = (Button) findViewById(R.id.editLog);
        uReservations = (Button) findViewById(R.id.uReservations);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("NYT OLLAAN ONDATACHANGESSA");
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                tvName.setText(userProfile.getUserName());
                tvSecond.setText(userProfile.getUserSecond());
                tvPhone.setText(userProfile.getUserPhone());
                tvAdress.setText(userProfile.getUserAdress());
                tvTown.setText(userProfile.getUserTown());
                tvPost.setText(userProfile.getUserPost());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Databasesta luku epäonnistui.");
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.homebutton6)){
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));

                } else if (v == findViewById(R.id.editProfile)){
                    startActivity(new Intent(ProfileActivity.this, Update_profileActivity.class));

                } else if (v == findViewById(R.id.editLog)) {
                    startActivity(new Intent(ProfileActivity.this, ChangepasswordActivity.class));

                } else if (v == findViewById(R.id.uReservations)){

                    startActivity(new Intent(ProfileActivity.this, UserReservationActivity.class));
                }

            }
        };

        homebutton6.setOnClickListener(listener);
        editProfile.setOnClickListener(listener);
        editLog.setOnClickListener(listener);
        uReservations.setOnClickListener(listener);
    }
}
