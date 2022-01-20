package com.example.easypark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;


public class Profile extends AppCompatActivity {
    TextView points;
    TextView username;
    int user_points;
    TextView email;
    TextView phone_number;
    TextView id;
    ImageView user_image;
    Button SpendPoints ;
    class GetData extends AsyncTask<Void , Void , Void>{
        URL text ;
        BufferedReader reader ;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                text = new URL("http://10.0.2.2:3000/get_data/" + currentUser.getEmail());
                HttpURLConnection http
                        = (HttpURLConnection) text.openConnection();
                http.connect();
                InputStream stream = http.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                http.disconnect();
                JSONObject json = new JSONObject(sb.toString());
                email.setText((String) json.get("email"));
                username.setText((String) json.get("name"));
                phone_number.setText((String) json.get("phone_num"));
                points.setText(String.valueOf(json.get("points")) + " Points");
                user_points = (int) json.get("points");
                id.setText(String.valueOf(json.get("student_id")));

            }
            catch(IOException | JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        GetData dd = new GetData();
        dd.execute();
        BottomNavigationView ButNavView = findViewById(R.id.bottom_navigation) ;
        ButNavView.setSelectedItemId(R.id.Prof);

        ButNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.Checkout:
                        Checkout ck = new Checkout((String) email.getText(), Profile.this );
                        ck.execute();
                        break ;
                    case R.id.ParkingSpaces:
                        Intent Parking = new Intent(getApplicationContext() , ParkingSpaces.class);
                        startActivity(Parking);
                        break;

                    case R.id.Map:
                        Intent Map = new Intent(getApplicationContext() , MapsActivity.class);
                        startActivity(Map);
                        break;
                }
                return false;
            }

        });


        //Linking to all elements inside the XML file
        points = (TextView) findViewById(R.id.Points);
        username = (TextView) findViewById(R.id.Username);
        phone_number = (TextView) findViewById(R.id.phone_number);
        id = (TextView) findViewById(R.id.Address);
        email = (TextView) findViewById(R.id.Email);
        user_image = (ImageView) findViewById(R.id.profile_image);

        SpendPoints = findViewById(R.id.viewpoints);
        SpendPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Points = new Intent(getApplicationContext() , Points.class);
                Points.putExtra("user_points", user_points);
                Points.putExtra("id" , id.getText());
                startActivity(Points);
            }
        });


    }
}