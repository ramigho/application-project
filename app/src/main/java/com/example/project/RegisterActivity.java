package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button home;
    EditText email;
    EditText password;
    EditText password2; // TODO Lisää myöhemmin
    TextView login;

    EditText firstname, secondname, phonenum, adress, town, postnum;
    String emailStr, passwordStr, firstnameStr, secondnameStr, phonenumStr, adressStr, townStr, postnumStr;
    Button register;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = mFirebaseAuth.getInstance();
        firebaseDatabase = firebaseDatabase.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        firstname = (EditText) findViewById(R.id.firstname);
        secondname = (EditText) findViewById(R.id.secondname);
        phonenum = (EditText) findViewById(R.id.phonenum);
        adress = (EditText) findViewById(R.id.adress);
        town = (EditText) findViewById(R.id.town);
        postnum = (EditText) findViewById(R.id.postnum);
        register = (Button) findViewById(R.id.register);
        login = (TextView) findViewById(R.id.login);
        home = (Button) findViewById(R.id.homebutton2);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.register)) {
                    emailStr = email.getText().toString();
                    passwordStr = password.getText().toString();
                    firstnameStr = firstname.getText().toString();
                    secondnameStr = secondname.getText().toString();
                    phonenumStr = phonenum.getText().toString();
                    adressStr = adress.getText().toString();
                    townStr = town.getText().toString();
                    postnumStr = postnum.getText().toString();

                    // TODO lue tämä rekisteröinnissä, tee "sama" kirjautumiseen
                    /* Jos jokin on tyhjä, ilmoitetaan asiasta. Syötetyt arvot pidetään.
                    if (emailStr.isEmpty()){
                        email.setError("Syötä sähköpostiosoite.");
                        email.requestFocus();
                    } / Tämä kopioidaan myös kaikkiin muihin. */

                    if (!(emailStr.isEmpty() && passwordStr.isEmpty())) {
                        mFirebaseAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Rekisteröinti epäonnistui, yritä uudelleen.", Toast.LENGTH_LONG);
                                } else {
                                    registerToDatabase();
                                    System.out.println("Jes!");
                                    mFirebaseAuth.signOut();
                                    finish();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Jossain tuli virhe:(", Toast.LENGTH_LONG);
                    }

                } else if (v == findViewById(R.id.homebutton2)){
                    finish();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
            }
        };

        register.setOnClickListener(listener);
        home.setOnClickListener(listener);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }
    public void registerToDatabase(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(mFirebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(firstnameStr, secondnameStr, phonenumStr, adressStr, townStr, postnumStr);
        myRef.setValue(userProfile);
    }
}