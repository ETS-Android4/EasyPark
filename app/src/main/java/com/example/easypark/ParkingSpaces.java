package com.example.easypark;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
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


public class ParkingSpaces extends AppCompatActivity {
    TextView pepsi_count;
    TextView gardens_count;
    TextView omarmohsen_count ;
    TextView busgate_count;
    TextView watson_count ;

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;


    class ParkingSpacesStatus extends AsyncTask<Void, Void, JSONObject> {

        URL text = null;
        BufferedReader reader = null;

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                text = new URL("http://10.0.2.2:3000/allParkings");
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
                Log.i("TAG", String.valueOf(json));
                return json;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject Status) {
            super.onPostExecute(Status);

            JSONObject json = new JSONObject();
            try {
                json.put("Empty"  , 0 );
                json.put("Almost Empty"  , 25 );
                json.put("Halfway Filled"  , 50 );
                json.put("Almost Filled" , 75);
                json.put("No Parking" , 100);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                pepsi_count.setText(String.valueOf(json.get((String) Status.get("Pepsi Gate")) + "%"));
                omarmohsen_count.setText(String.valueOf(json.get((String) Status.get("Omar Mohsen")) + "%"));
                busgate_count.setText(String.valueOf(json.get((String) Status.get("Bus Gate")) + "%"));
                gardens_count.setText(String.valueOf(json.get((String) Status.get("Gardens")) + "%"));
                watson_count.setText(String.valueOf(json.get((String) Status.get("Watson")) + "%"));




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_spaces);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String id ;
        ParkingSpacesStatus ps = new ParkingSpacesStatus();
        ps.execute();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) //Receiving needed data from activity two screen
        {
            id = bundle.getString("ID");
            Log.i("TAG", id);
        }

        BottomNavigationView ButNavView = findViewById(R.id.bottom_navigation) ;
        ButNavView.setSelectedItemId(R.id.ParkingSpaces);

        ButNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.Checkout:
                        Checkout ck = new Checkout(currentUser.getEmail(), ParkingSpaces.this );
                        ck.execute();
                        break ;
                    case R.id.Prof:
                        Intent Prof = new Intent(getApplicationContext() , Profile.class);
                        startActivity(Prof);
                        break;

                    case R.id.Map:
                        Intent Map = new Intent(getApplicationContext() , MapsActivity.class);
                        startActivity(Map);
                        break;
                    case R.id.signout:
                        signOut();
                        revokeAccess();
                        Intent nn = new Intent(getApplicationContext(),Signin.class);
                        startActivity(nn);
                        break;
                }
                return false;
            }

        });
        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);
        TextView pepsi = (TextView) findViewById(R.id.textView);
        TextView omar_mohsen = (TextView) findViewById(R.id.textView2);
        TextView bus = (TextView) findViewById(R.id.textView3);
        TextView gardens = (TextView) findViewById(R.id.textView32);
        TextView watson = (TextView) findViewById(R.id.textView322);

        pepsi_count = findViewById(R.id.pepsi);
        omarmohsen_count  = findViewById(R.id.mohsen);
        busgate_count = findViewById(R.id.bus);
        gardens_count = findViewById(R.id.gardens);
        watson_count = findViewById(R.id.watson);


        double[] Latitudes = {30.0166705 , 30.0218412 , 30.0176783 , 30.0220150 , 30.0231389};
        double []Longitudes = {31.5016696 , 31.5011342  , 31.4997065 , 31.4971187 , 31.5011342};

        Log.i("Current User", String.valueOf(currentUser.getEmail()));

        TextView []ParkingSpaces = {pepsi , omar_mohsen , bus, gardens, watson};
        TextView []ParkingsStatus = {pepsi_count ,  gardens_count , busgate_count,omarmohsen_count, watson_count};

        Intent ParkingPage = new Intent(this , ParkingPage.class);
        String [] Colors = {"#3597d8", "#33cb74", "#edc213", "#e55040", "#9a59b3"};

        Log.i("Omar Mohsen", (String) omarmohsen_count.getText());
        Log.i("Gardens", (String) gardens_count.getText());

        for (int i = 0 ; i < ParkingSpaces.length ; i++){
            final int index = i;
            ParkingSpaces[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView temp = (TextView) view;
                    ParkingPage.putExtra("Color" , Colors[index]);
                    ParkingPage.putExtra("Parking_Space" ,  temp.getText()) ;
                    ParkingPage.putExtra("Parking_Status" , ParkingsStatus[index].getText());
                    ParkingPage.putExtra("Email" , String.valueOf(currentUser.getEmail()));
                    ParkingPage.putExtra("parking_id" , String.valueOf(index+1));
                    ParkingPage.putExtra("latitude" , Latitudes[index]);
                    ParkingPage.putExtra("longitude" , Longitudes[index]);
                    startActivity(ParkingPage);
                }
            });

        }

    }
    private void signOut() {
        // Firebase sign out
        firebaseAuth.signOut();
        googleSignInClient.signOut();
        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Google Sign In failed, update UI appropriately
                        Log.w("TAG", "Signed out of google");
                    }
                });
    }
    private void revokeAccess() {
        // Firebase sign out
        firebaseAuth.signOut();
        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Google Sign In failed, update UI appropriately
                        Log.w("TAG", "Revoked Access");
                    }
                });
    }

}