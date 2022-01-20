package com.example.easypark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    Button signInbtn;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        create = findViewById(R.id.button5);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Account has been created successfully", Toast.LENGTH_LONG).show();
                signInActivity();
            }
        });

        signInbtn= findViewById(R.id.button);
        signInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInActivity();
            }
        });
    }

    public void signInActivity() {
        Intent intent = new Intent(this, Signin.class);
        startActivity(intent);
    }
}