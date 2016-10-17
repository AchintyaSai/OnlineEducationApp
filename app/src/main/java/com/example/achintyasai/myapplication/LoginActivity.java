package com.example.achintyasai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
String name,passwd;
    Button suwg;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        suwg = (Button)findViewById(R.id.suwg);


        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SharedPreferences prefs = getSharedPreferences("TEAM_REQ", MODE_PRIVATE);
        name = prefs.getString("name", "");//"No name defined" is the default value.
        passwd = prefs.getString("password",""); //0 is the default value.


        final EditText username =(EditText)findViewById(R.id.un);
        final EditText password =(EditText)findViewById(R.id.password);
        username.setText(name);
        password.setText(passwd);
        Button login = (Button)findViewById(R.id.login);
        Button signin = (Button)findViewById(R.id.signin);
        final CheckBox remcred = (CheckBox)findViewById(R.id.remcred);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Signup.class);
                startActivity(intent);
                finish();
            }
        });

        suwg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, 9001);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remcred.isChecked()==true)
                {
                    SharedPreferences.Editor editor = getSharedPreferences("TEAM_REQ", MODE_PRIVATE).edit();
                    editor.putString("name", username.getText().toString());
                    editor.putString("password",password.getText().toString() );
                    editor.commit();
                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpURLConnection con = (HttpURLConnection) (new URL(IpAddress.ip_address+"/login.php?email="+username.getText().toString()+"&pass="+password.getText().toString())).openConnection();
                        con.setRequestMethod("GET");
                        con.setDoInput(true);
                        con.setDoOutput(true);
                        con.connect();
                        InputStream is = con.getInputStream();
                        BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                        String a[] = new String[10];
                        a[0] = "0";
                        a[1] = "0";
                        a[2] = "0";
                        a[3] = "0";
                        a[4] = "0";
                        String line = "";
                        int i = -1;
                        while ((line = bufferedWriter.readLine()) != null) {
                            i++;
                            a[i] = line;
                            Log.i("a["+i+"]",a[i]);
                        }
                        if(a[0]=="0"||a[1]=="0") {
                            Toast.makeText(LoginActivity.this, "wrong credentials", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                            intent.putExtra("user_id",a[2]);
                            intent.putExtra("user_name",a[0]);
                            intent.putExtra("latitude",a[3]);
                            intent.putExtra("longitude",a[4]);
                            startActivity(intent);
                            finish();

                            bufferedWriter.close();
                            is.close();
                            con.disconnect();
                        }
                    } catch (Exception e) {
                        Log.e("log_tag", "Error in http connection" + e.toString());
                        username.setText("");
                        password.setText("");
                    }
                }
                else
                {
                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpURLConnection con = (HttpURLConnection) (new URL(IpAddress.ip_address+"/login.php?email="+username.getText().toString()+"&pass="+password.getText().toString())).openConnection();
                        con.setRequestMethod("GET");
                        con.setDoInput(true);
                        con.setDoOutput(true);
                        con.connect();
                        InputStream is = con.getInputStream();
                        BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                        String a[] = new String[10];
                        a[0] = "0";
                        a[1] = "0";
                        a[2] = "0";
                        a[3] = "0";
                        a[4] = "0";
                        String line = "";
                        int i = -1;
                        while ((line = bufferedWriter.readLine()) != null) {
                            i++;
                            a[i] = line;
                            Log.i("a["+i+"]",a[i]);

                        }
                        if(a[0]=="0"||a[1]=="0") {
                            Toast.makeText(LoginActivity.this, "wrong credentials", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                            intent.putExtra("user_id",a[2]);
                            intent.putExtra("user_name",a[0]);
                            intent.putExtra("latitude",a[3]);
                            intent.putExtra("longitude",a[4]);
                            startActivity(intent);
                            finish();

                            bufferedWriter.close();
                            is.close();
                            con.disconnect();
                        }
                    } catch (Exception e) {
                        Log.e("log_tag", "Error in http connection" + e.toString());
                        username.setText("");
                        password.setText("");
                    }

                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9001) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                String givenemail = acct.getEmail();
                String givenname = acct.getGivenName();
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    HttpURLConnection con = (HttpURLConnection) (new URL(IpAddress.ip_address+"/login.php?email="+givenemail+"&pass=google")).openConnection();
                    con.setRequestMethod("GET");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.connect();
                    InputStream is = con.getInputStream();
                    BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                    String a[] = new String[10];
                    a[0] = "0";
                    a[1] = "0";
                    a[2] = "0";
                    a[3] = "0";
                    a[4] = "0";
                    String line = "";
                    int i = -1;
                    while ((line = bufferedWriter.readLine()) != null) {
                        i++;
                        a[i] = line;
                        Log.i("a["+i+"]",a[i]);
                    }
                    if(a[0]=="0"||a[1]=="0") {
                        Intent intent = new Intent(LoginActivity.this,googlesignin.class);
                intent.putExtra("username",givenname);
                intent.putExtra("email",givenemail);
                startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                        intent.putExtra("user_id",a[2]);
                        intent.putExtra("user_name",a[0]);
                        intent.putExtra("latitude",a[3]);
                        intent.putExtra("longitude",a[4]);
                        startActivity(intent);
                        finish();

                        bufferedWriter.close();
                        is.close();
                        con.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection" + e.toString());
                }






            }
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
