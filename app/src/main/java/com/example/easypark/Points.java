package com.example.easypark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.Random;


public class Points extends AppCompatActivity {
    ListView list;
    int user_points ;
    String id;

    class sendCode extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                URL text = new URL("http://10.0.2.2:3000/get_offer_points/" + params[0]);
                HttpURLConnection http = (HttpURLConnection) text.openConnection();
                InputStream stream = http.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                JSONObject json = new JSONObject(sb.toString());
                int Points = (int) json.get("points");
                Random rand = new Random(); //instance of random class
                int upperbound = 1000000;
                //generate random values from 0-24
                int int_random = rand.nextInt(upperbound);
                String message_body = "Code for offer is: ";
                if (user_points >= Points){
                    if(ContextCompat.checkSelfPermission(
                            getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("5554",
                                null,
                                message_body+String.valueOf(int_random),
                                null,
                                null);
                        }

                    else{
                        String[] perms = {"android.permission.SEND_SMS"};
                        int permsRequestCode = 200;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(perms, permsRequestCode);
                        }
                    }


                    URL text1 = new URL("http://10.0.2.2:3000/change_points/" + id + "/" + String.valueOf(-Points));
                    HttpURLConnection urlConn2 = (HttpURLConnection) text1.openConnection();
                    urlConn2.setDoOutput(true);
                    Log.i("TAG2" , String.valueOf(urlConn2.getResponseCode()));
                    return 200;
                }
                else{
                    return 400;
                }
            }
            catch (IOException | JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer == 400){
                Toast.makeText(getApplicationContext() , "Insufficient Points!" , Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext() , "A Message has been sent with your promo code!" , Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           String[] permissions, int[] gRes ) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, gRes);
        switch (permsRequestCode) {
            case 200:
                if (gRes.length > 0) {
                    if(gRes[0]== PackageManager.PERMISSION_GRANTED) {
                        SmsManager smsManager = SmsManager.getDefault();
                        Random rand = new Random(); //instance of random class
                        int upperbound = 1000000;
                        //generate random values from 0-24
                        int int_random = rand.nextInt(upperbound);
                        String message_body = "Code for offer is: ";
                        smsManager.sendTextMessage("5554",
                                null,
                                message_body+String.valueOf(int_random),
                                null,
                                null);                      }
                    else{
                        Log.i("PermissionsResult", "Not Granted");
                    }
                }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.points);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) //Receiving needed data from activity two screen
        {
            user_points = bundle.getInt("user_points");
            id = bundle.getString("id");
        }
        list = (ListView) findViewById(R.id.list_manager);
        Integer images [] = {R.drawable.tarwe2a,R.drawable.tabali, R.drawable.cilantro,R.drawable.subway};
        String [] vendor_name_cost = {"Tarwee2a : 50","Tabali : 30","Cilantro : 100", "Subway : 30"};
        String [] vendor_offer = {"Get 50% off","Get 30% off","Get 40% off", "Get 20% off"};

        CustomAdapter List_genres = new CustomAdapter(this, vendor_name_cost, images,vendor_offer);
        list.setAdapter(List_genres);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                sendCode s = new sendCode();
                s.execute(i + 1);
                //Toast.makeText( getApplicationContext(), "An SMS is being sent to you", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class CustomAdapter extends ArrayAdapter {
        private String[] vendor_name_cost;
        private Integer[] imageid;
        private String [] vendor_offer;
        private Activity context;

        public CustomAdapter(Activity context, String [] vendor_name_cost, Integer[] imageid, String [] vendor_offer) {
            super(context, R.layout.row_item, vendor_name_cost);
            this.context = context;
            this.vendor_name_cost = vendor_name_cost;
            this.vendor_offer = vendor_offer;
            this.imageid = imageid;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row=convertView;


            LayoutInflater inflater = context.getLayoutInflater();
            if(convertView==null)
                row = inflater.inflate(R.layout.row_item, null, true);
            TextView textViewMovie = (TextView) row.findViewById(R.id.vendor_name_cost);
            TextView textViewMovie1 = (TextView) row.findViewById(R.id.vendor_offer);
            ImageView genre_image = (ImageView) row.findViewById(R.id.vendor_image);

            textViewMovie.setText(vendor_name_cost[position]);
            textViewMovie1.setText(vendor_offer[position]);
            genre_image.setImageResource(imageid[position]);
            return  row;
        }
    }
}