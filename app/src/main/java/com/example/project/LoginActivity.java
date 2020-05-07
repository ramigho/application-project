package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;
    Button home;
    TextView signUp;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        home = (Button) findViewById(R.id.homebutton3);
        signUp = (TextView) findViewById(R.id.signUp);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT);
                    //
                } else {
                    Toast.makeText(LoginActivity.this, "Please login.", Toast.LENGTH_SHORT);
                }
            }
        };


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.login)) {
                    String emailStr = email.getText().toString();
                    String passwordStr = password.getText().toString();

                    /* Jos jokin on tyhjä, ilmoitetaan asiasta. Syötetyt arvot pidetään.
                    if (emailStr.isEmpty()){
                        email.setError("Syötä sähköpostiosoite.");
                        email.requestFocus();
                    } / Tämä kopioidaan myös kaikkiin muihin. */

                    if (!(emailStr.isEmpty() && passwordStr.isEmpty())) {
                        mFirebaseAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login error, please login again.", Toast.LENGTH_SHORT);
                                } else {
                                    System.out.println("ONNISTUI!!!");
                                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(mainIntent);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Jossain tuli virhe:(", Toast.LENGTH_SHORT);
                    }

                } else if (v == findViewById(R.id.homebutton3)){
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };

        login.setOnClickListener(listener);
        home.setOnClickListener(listener);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
