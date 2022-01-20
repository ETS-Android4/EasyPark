package com.example.easypark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class MissingData extends AppCompatActivity {
    EditText name_in ;
    EditText id_in;
    String email;
    EditText phone_in;
    class SendDeviceDetails extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String data = "";

            URL text1 = null;
            try {
                String id  = String.valueOf(id_in.getText());
                String phone  =  String.valueOf(phone_in.getText());
                String name = String.valueOf(name_in.getText());
                text1 = new URL("http://10.0.2.2:3000/store_data/" +  name  + "/"+ phone + "/" + email + "/" + id );
                HttpURLConnection urlConn = (HttpURLConnection) text1.openConnection();
                urlConn.setDoOutput(true);
                Log.i("TAG", urlConn.getResponseMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;     }

        @Override
        protected void onPostExecute(Void unused) {
            Intent next_page = new Intent(getApplicationContext(),  ParkingSpaces.class);
            startActivity(next_page);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filldata);
         name_in = findViewById(R.id.name);
         id_in = findViewById(R.id.student_id);
         phone_in = findViewById(R.id.phonenumber);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        email = currentUser.getEmail();

        Button Submit = findViewById(R.id.button2);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendDeviceDetails ss = new SendDeviceDetails();
                ss.execute();
            }
        });







    }
}