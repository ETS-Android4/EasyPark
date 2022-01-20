package com.example.easypark;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

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

public class Checkout extends AsyncTask<Void, Void, Integer> {
    String email;
    String Status;
    Context c  ;
    public Checkout(String email , Context c){
        this.email = email ;
        this.c = c;

    }
    @Override
    protected Integer doInBackground(Void... voids) {
        URL text1 = null;
        URL text = null;
        URL url1 = null;
        BufferedReader reader;
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

            url1 = new URL("http://10.0.2.2:3000/getUserParking/" + id );
            http = (HttpURLConnection) url1.openConnection();
            stream = http.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            sb = new StringBuilder();
            line = "";

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            json = new JSONObject(sb.toString());
            String ParkingSpace = String.valueOf(json.get("message"));
            text1 = new URL("http://10.0.2.2:3000/checkout/" +id);
            HttpURLConnection urlConn = (HttpURLConnection) text1.openConnection();
            urlConn.setDoOutput(true);
            Log.i("TAG", String.valueOf(urlConn.getResponseCode()));

            if (urlConn.getResponseCode() == 200) {

                url1 = new URL("http://10.0.2.2:3000/updateCurrentCount/-1/" + ParkingSpace);
                HttpURLConnection urlConn1 = (HttpURLConnection) url1.openConnection();
                urlConn1.setDoOutput(true);
                return urlConn1.getResponseCode();
            } else
                return 400;
        }
        catch(IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer == 200){
            AlertDialog dlg = new AlertDialog.Builder(c).setTitle("Message")
                    .setMessage("Checked-out from parking successfully").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create();
            dlg.show();
        }
        else{
            AlertDialog dlg = new AlertDialog.Builder(c).setTitle("Message")
                    .setMessage("You didn't record parking!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create();
            dlg.show();
        }
    }
}
