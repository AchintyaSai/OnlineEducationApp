package com.example.achintyasai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Signup extends AppCompatActivity {
    RequestQueue requestQueue;
    EditText username;
    EditText password;
    EditText retype;
    EditText address;
    EditText phnum;
    EditText subject;
    Button signin,getloc;
    RadioButton teacher,student;
    GPStrace gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String[] latitude = {"0.000"};
        final String[] longitude = {" 0.0 "};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Signup through MEETUTU");
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ArrayList <String> list = getResults(IpAddress.ip_address+"/subjects.php");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        AutoCompleteTextView subject1 = (AutoCompleteTextView) findViewById(R.id.subject);
        subject1.setThreshold(1);//will start working from first character
        subject1.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        username = (EditText) findViewById(R.id.usrname);
        password = (EditText) findViewById(R.id.password);
        retype = (EditText) findViewById(R.id.retypepassword);
        address = (EditText) findViewById(R.id.address1);
        phnum = (EditText) findViewById(R.id.phnum);
        subject = (EditText) findViewById(R.id.subject);
        signin = (Button) findViewById(R.id.signin);
        teacher = (RadioButton)findViewById(R.id.Teacher);
        student = (RadioButton)findViewById(R.id.student);
        getloc = (Button)findViewById(R.id.getloc);
        signin.setEnabled(false);



        getloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps=new GPStrace(Signup.this);
                if(gps.canGetLocation()){
                    latitude[0] =Double.toString(gps.getLatitude());
                    longitude[0] =Double.toString(gps.getLongtiude());
                    Toast.makeText(getApplicationContext(),"Your Location is \nLat:"+ latitude[0] +"\nLong:"+ longitude[0], Toast.LENGTH_LONG).show();
                    signin.setEnabled(true);
                }
                else
                {
                    gps.showSettingAlert();
                }
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un1 = username.getText().toString().replaceAll(" ","%20");
                String subject1 = subject.getText().toString().replaceAll(" ","%20");
                String role="";
                if(un1.equals("")||address.getText().toString().equals("")||password.getText().toString().equals("")||retype.getText().toString().equals("")||phnum.getText().toString().equals("")||subject1.equals("")){
                    Toast.makeText(getBaseContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }else{
                    String pass = password.getText().toString();
                    String rety = retype.getText().toString();
                    if(teacher.isChecked())
                        role="teacher";
                    else if(student.isChecked())
                        role ="student";
                    if (pass.equals(rety)) {
                        PostingUsingVolleyLibrary pv = new PostingUsingVolleyLibrary(getBaseContext());
                        Log.i("lat",latitude[0]);
                        Log.i("long",longitude[0]);
                        if(!pv.postToweb(username.getText().toString(),address.getText().toString(),password.getText().toString(),phnum.getText().toString(),subject.getText().toString(),""+role,latitude[0],longitude[0]))
                        {
                            Intent intent = new Intent(Signup.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(), "Problem in signing", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(Signup.this, "password doesnt match.try again", Toast.LENGTH_LONG).show();
                        password.setText("");
                        retype.setText("");
                    }

                }
            };
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
