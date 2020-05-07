package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.week10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_profileActivity extends AppCompatActivity {

    Button homebutton4;
    Button saveButton;
    EditText editName, editSecond, editPhone, editAdress, editTown, editPost;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);

        homebutton4 = (Button) findViewById(R.id.homebutton4);
        saveButton = (Button) findViewById(R.id.saveButton);
        editName = (EditText) findViewById(R.id.editName);
        editSecond = (EditText) findViewById(R.id.editSecond);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editAdress = (EditText) findViewById(R.id.editAdress);
        editTown = (EditText) findViewById(R.id.editTown);
        editPost = (EditText) findViewById(R.id.editPost);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("NYT OLLAAN PROFILEUPDATESSA");
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                editName.setText(userProfile.getUserName());
                editSecond.setText(userProfile.getUserSecond());
                editPhone.setText(userProfile.getUserPhone());
                editAdress.setText(userProfile.getUserAdress());
                editTown.setText(userProfile.getUserTown());
                editPost.setText(userProfile.getUserPost());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Databasesta luku epäonnistui.");
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.saveButton)){
                    String name = editName.getText().toString();
                    String second = editSecond.getText().toString();
                    String phone = editPhone.getText().toString();
                    String adress = editAdress.getText().toString();
                    String town = editTown.getText().toString();
                    String post = editPost.getText().toString();

                    UserProfile userProfile = new UserProfile(name, second, phone, adress, town, post);

                    databaseReference.setValue(userProfile);

                    finish();
                } if (v == findViewById(R.id.homebutton4)){
                    startActivity(new Intent(Update_profileActivity.this, MainActivity.class));
                }
            }
        };

        saveButton.setOnClickListener(listener);
        homebutton4.setOnClickListener(listener);
    }
}