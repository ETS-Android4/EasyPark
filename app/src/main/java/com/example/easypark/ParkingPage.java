package com.example.easypark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class ParkCar extends AsyncTask<Void, Void, Integer>
{
    String parking_id;
    String email;
    String Status;
    Context c  ;
    public ParkCar(String parking_id ,  String email , String Status, Context c){
        this.parking_id = parking_id ;
        this.email = email ;
        this.Status = Status;
        this.c = c;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        URL text = null;
        URL text1 = null;
        URL url1 = null;
        BufferedReader reader;
        int NumericalStatus ;
        if (Status.equals("Empty"))
            NumericalStatus = 0;
        else if (Status.equals("Almost Empty"))
            NumericalStatus = 25;
        else if (Status.equals("Halfway Filled"))
            NumericalStatus = 50;
        else if (Status.equals("Almost Filled"))
            NumericalStatus = 75;
        else
            NumericalStatus = 100;
        try {

            text = new URL("http://10.0.2.2:3000/Get_ID/" + this.email);
            HttpURLConnection http = (HttpURLConnection) text.openConnection();
            InputStream stream = http.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            JSONObject json = new JSONObject(sb.toString());
            String id = String.valueOf(json.get("student_id"));

            text = new URL("http://10.0.2.2:3000/getUserParking/" + id);
            http = (HttpURLConnection) text.openConnection();
            stream = http.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            sb = new StringBuilder();
            line = "";

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            json = new JSONObject(sb.toString());
            http.disconnect();


            Log.i("TAG" , String.valueOf(http.getResponseCode()));
            if (json.get("message").equals("Not Parked")){
                text1 = new URL("http://10.0.2.2:3000/updateUserParking/"  + id + "/" + this.parking_id);
                HttpURLConnection urlConn = (HttpURLConnection) text1.openConnection();
                urlConn.setDoOutput(true);
                Log.i("TAG1" , String.valueOf(urlConn.getResponseCode()));


                url1 = new URL("http://10.0.2.2:3000/updateCurrentCount/1/" + this.parking_id);
                HttpURLConnection urlConn1 = (HttpURLConnection) url1.openConnection();
                urlConn1.setDoOutput(true);
                Log.i("TAG2" , String.valueOf(urlConn1.getResponseCode()));

                url1 = new URL("http://10.0.2.2:3000/change_points/" + id + "/10");
                HttpURLConnection urlConn2 = (HttpURLConnection) url1.openConnection();
                urlConn2.setDoOutput(true);
                Log.i("TAG2" , String.valueOf(urlConn2.getResponseCode()));

                text1 = new URL("http://10.0.2.2:3000/send_new_report/"  + this.parking_id + "/" + String.valueOf(NumericalStatus));
                HttpURLConnection urlConn3 = (HttpURLConnection) text1.openConnection();
                urlConn3.setDoOutput(true);
                Log.i("TAG1" , String.valueOf(urlConn3.getResponseCode()));

                return urlConn3.getResponseCode();
            }
            else{
                return 400;
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(integer == 200) {
            AlertDialog dlg = new AlertDialog.Builder(c).setTitle("Message")
                    .setMessage("Parking recorded Successfully , You Received 10 Points").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create();
            dlg.show();
        }
        else{
            AlertDialog dlg = new AlertDialog.Builder(c).setTitle("Message")
                    .setMessage("You Already Registered Parking!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create();
            dlg.show();
        }

    }
}
class ReportParkingStatus extends AsyncTask<Void, Void, Integer> {
    String parking_id;
    String email;
    String Status;
    Context c  ;
    public ReportParkingStatus(String parking_id , String Status , String email ,Context c) {
        this.email = email;
        this.parking_id = parking_id ;
        this.c = c;
        this.Status = Status;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        URL text1 = null;
        URL text = null;
        int NumericalStatus ;
        if (Status.equals("Empty"))
            NumericalStatus = 0;
        else if (Status.equals("Almost Empty"))
            NumericalStatus = 25;
        else if (Status.equals("Halfway Filled"))
            NumericalStatus = 50;
        else if (Status.equals("Almost Filled"))
            NumericalStatus = 75;
        else
            NumericalStatus = 100;

        try {
            text = new URL("http://10.0.2.2:3000/Get_ID/" + this.email);
            HttpURLConnection http = (HttpURLConnection) text.openConnection();
            InputStream stream = http.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            JSONObject json = new JSONObject(sb.toString());
            String id = String.valueOf(json.get("student_id"));

            text1 = new URL("http://10.0.2.2:3000/send_new_report/"  + this.parking_id + "/" + String.valueOf(NumericalStatus));
            HttpURLConnection urlConn = (HttpURLConnection) text1.openConnection();
            urlConn.setDoOutput(true);
            Log.i("TAG1" , String.valueOf(urlConn.getResponseCode()));

            text1 = new URL("http://10.0.2.2:3000/change_points/" + id + "/10");
            HttpURLConnection urlConn2 = (HttpURLConnection) text1.openConnection();
            urlConn2.setDoOutput(true);
            Log.i("TAG2" , String.valueOf(urlConn2.getResponseCode()));

            return urlConn2.getResponseCode();
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer == 200){
            AlertDialog dlg = new AlertDialog.Builder(c).setTitle("Message")
                    .setMessage("Parking status report sent successfully , You Receive 10 Points").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create();
            dlg.show();
        }
    }
}

public class ParkingPage extends AppCompatActivity {
    ProgressBar ParkingProgress;
    TextView ParkingTitle;
    TextView ParkingStatus;
    Button Park;
    Button Report;
    RadioGroup ParkingStatusRadio;
    Dialog Report_PopUp;
    Dialog Park_Car;
    String Color_code;
    String title;
    String[] StatusStrings;
    String RadioStatusReported ;
    String s;
    LocationManager locationManager;
    Location loc;
    LocationListener locationListener;


    String email;
    String parking_id;
    double latitude ;
    double longitude ;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                loc = location;
                Log.i("Location: " , String.valueOf(loc));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ParkingPage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }


        StatusStrings = getResources().getStringArray(R.array.size_entries);
        Report_PopUp = new Dialog(this);
        Park_Car = new Dialog(this);

        ParkingProgress = findViewById(R.id.ParkingProgress);
        ParkingTitle = findViewById(R.id.ParkingTitle);
        ParkingStatus = findViewById(R.id.ParkingStatus);
        ParkingStatusRadio = findViewById(R.id.StatusReports);
        Park = findViewById(R.id.ParkCar);
        Report = findViewById(R.id.ReportStatus);
        ParkingStatusRadio.clearCheck();
        Park.setEnabled(false);
        Report.setEnabled(false);
        Park.setBackgroundColor(Color.GRAY);
        Report.setBackgroundColor(Color.GRAY);
        Park.setTextColor(Color.parseColor("#A9A9A9"));
        Report.setTextColor(Color.parseColor("#A9A9A9"));
        ParkingStatusRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                RadioStatusReported = (String) radioButton.getText();
                Park.setEnabled(true);
                Report.setEnabled(true);
                Park.setBackgroundColor(Color.WHITE);
                Report.setBackgroundColor(Color.WHITE);
                Park.setTextColor(Color.BLACK);
                Report.setTextColor(Color.BLACK);
            }
        });



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) //Receiving needed data from activity two screen
        {
            title = bundle.getString("Parking_Space");
            Color_code = bundle.getString("Color");
            parking_id =  bundle.getString("parking_id");
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
            email = bundle.getString("Email");
            s = bundle.getString("Parking_Status");
            ParkingTitle.setText(title);
            ConstraintLayout x = findViewById(R.id.Main);
            x.setBackgroundColor(Color.parseColor(Color_code));
        }

        ParkingStatus.setText("Parking Status: " + s + " of the spots has been occupied");

        ParkingProgress.setProgress(Integer.parseInt(s.split("%")[0]));


        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportParkingStatus rp = new ReportParkingStatus(parking_id, RadioStatusReported, email ,ParkingPage.this);
                rp.execute();
            }
        });

        Park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float[] distance = new float[1];

                if (loc == null)
                    Toast.makeText(ParkingPage.this, "Please Wait while we detect your location" , Toast.LENGTH_SHORT).show();
                else{
                Location.distanceBetween(latitude, longitude,
                        loc.getLatitude(), loc.getLongitude(), distance);
                if( distance[0] > 100 ){
                    AlertDialog dlg = new AlertDialog.Builder(ParkingPage.this).setTitle("Message")
                            .setMessage("Out of Parking range!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create();
                    dlg.show();

                } else {
                    ParkCar pk = new ParkCar(parking_id, email , RadioStatusReported, ParkingPage.this);
                    pk.execute();
                }
                }
            }
        });
    }
}