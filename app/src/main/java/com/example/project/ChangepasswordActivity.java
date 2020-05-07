package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.week10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangepasswordActivity extends AppCompatActivity {

    Button homebutton5;
    Button changePassword;
    EditText newPassword;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        homebutton5 = (Button) findViewById(R.id.homebutton5);
        changePassword = (Button) findViewById(R.id.changePassword);
        newPassword = (EditText) findViewById(R.id.newPassword);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.changePassword)){
                    String userPwd = newPassword.getText().toString();
                    firebaseUser.updatePassword(userPwd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ChangepasswordActivity.this, "Salasana vaihdettu.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                System.out.println("\n\nEi natsannut\n\n");
                            }
                        }
                    });

                } else if (v == findViewById(R.id.homebutton5)){
                    startActivity(new Intent(ChangepasswordActivity.this, MainActivity.class));
                }

            }
        };

        homebutton5.setOnClickListener(listener);
        changePassword.setOnClickListener(listener);
    }
}
