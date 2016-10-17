package com.example.achintyasai.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class googlesignin extends AppCompatActivity{
    GPStrace gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlesignin);
        setTitle("Signup through google");
        Bundle bundle = getIntent().getExtras();

        final double[] latitude = {0.000};
        final double[] longitude = { 0.0 };
        final String username = bundle.getString("username");
        final String email = bundle.getString("email");
        final EditText wn = (EditText)findViewById(R.id.wn);
        Button getloc = (Button)findViewById(R.id.getloc);
        final Button signin = (Button)findViewById(R.id.signin);
        ArrayList <String> list = getResults(IpAddress.ip_address+"/subjects.php");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        final AutoCompleteTextView subject = (AutoCompleteTextView) findViewById(R.id.subject);
        subject.setThreshold(1);//will start working from first character
        subject.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostingUsingVolleyLibrary pv = new PostingUsingVolleyLibrary(getBaseContext());
                if(!pv.postToweb(username,email,"google",wn.getText().toString(),subject.getText().toString(),"student",Double.toString(latitude[0]),Double.toString(longitude[0])))
                {
                    Intent intent = new Intent(googlesignin.this,LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Problem in signing", Toast.LENGTH_SHORT).show();
                }
            }
        });


        getloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps=new GPStrace(googlesignin.this);
                if(gps.canGetLocation()){
                    latitude[0] =gps.getLatitude();
                    longitude[0] =gps.getLongtiude();
                    Toast.makeText(getApplicationContext(),"Your Location is \nLat:"+ latitude[0] +"\nLong:"+ longitude[0], Toast.LENGTH_LONG).show();
                    signin.setEnabled(true);
                }
                else
                {
                    gps.showSettingAlert();
                }
            }
        });

    }

    public static ArrayList<String> getResults(String url)
    {
        ArrayList<String> list=null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            InputStream is = con.getInputStream();
            BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            String  line = "";
            list = new ArrayList<String>();
            while ((line = bufferedWriter.readLine()) != null) {
                list.add(line);
            }
            con.disconnect();
        }catch(Exception e)
        {

        }
        return list;
    }


}
