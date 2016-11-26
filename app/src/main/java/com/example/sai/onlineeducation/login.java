package com.example.sai.onlineeducation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Login Page");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login= (Button)findViewById(R.id.login);
        Button signup= (Button)findViewById(R.id.signup);
        final EditText usrname = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        usrname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    HttpURLConnection con = (HttpURLConnection) (new URL("http://"+Ip_Address.ip_address+"/login.php?un=" + usrname.getText().toString() + "&pass=" + password.getText().toString())).openConnection();
                    con.setRequestMethod("POST");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.connect();
                    InputStream is = con.getInputStream();
                    BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                    String a[] = new String[3];
                    a[0] = "0";
                    a[1] = "0";
                    a[2] = "0";
                    String line = "";
                    int i = 0;

                    while ((line = bufferedWriter.readLine()) != null) {
                        a[i] = line;
                        i++;
                    }
                    if(a[0]=="0"||a[1]=="0") {
                        Toast.makeText(login.this, "wrong credentials", Toast.LENGTH_SHORT).show();
                        }
                    else {
                        Intent intent = new Intent(login.this, profile1.class);
                        intent.putExtra("id",a[2]);
                        startActivity(intent);
                        finish();

                        bufferedWriter.close();
                        is.close();
                        con.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection " + e.toString());
                    usrname.setText("");
                    password.setText("");
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, post.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
