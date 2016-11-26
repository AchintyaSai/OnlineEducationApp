package com.example.sai.onlineeducation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class post extends AppCompatActivity{
    TextView Return;
EditText username;
    EditText password;
    EditText retype;
    EditText email;
    EditText rollnumber;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
       setTitle("Signup");
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        retype = (EditText)findViewById(R.id.retype);
        email = (EditText)findViewById(R.id.email);
        rollnumber = (EditText)findViewById(R.id.rollnumber);
        Return = (TextView)findViewById(R.id.Return);
        final String insert = "http://"+Ip_Address.ip_address+"/insertStudent.php";
        Button btn = (Button) findViewById(R.id.register);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(post.this, login.class);
                startActivity(intent);
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("pass",password.getText().toString());
                Log.i("repass",retype.getText().toString());
                String pass = password.getText().toString();
                String rety = retype.getText().toString();
                if (pass.equals(rety)) {
                    requestQueue = Volley.newRequestQueue(post.this);
                    StringRequest request = new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.e("success", "success");
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("failed", "failed");
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("usr_name", username.getText().toString());
                            parameters.put("passwd", password.getText().toString());
                            parameters.put("usr_id", rollnumber.getText().toString());
                            parameters.put("email", email.getText().toString());
                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                    Intent intent = new Intent(post.this, login.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(post.this, "password doesnt match.try again", Toast.LENGTH_LONG).show();
                    password.setText("");
                    retype.setText("");
                }
            };
        });


    }
}
