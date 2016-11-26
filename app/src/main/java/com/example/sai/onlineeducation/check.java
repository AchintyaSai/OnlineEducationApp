package com.example.sai.onlineeducation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class check extends Activity {
    int num;
    String sub_name, usr_id, test;
    RequestQueue requestQueue;
    int marks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Bundle extra = getIntent().getBundleExtra("answers");
        ArrayList<String> objects = (ArrayList<String>) extra.getSerializable("objects");
        Bundle bundle = getIntent().getExtras();
        usr_id = bundle.getString("usr_id");
        sub_name = bundle.getString("sub_name");
        test = bundle.getString("test");
        ArrayList<String> arraylist = new ArrayList<String>();
        Log.i("", objects.get(2));
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection con = (HttpURLConnection) (new URL("http://"+Ip_Address.ip_address+"/answers.php")).openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            InputStream is = con.getInputStream();
            BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            String line = "";
            while ((line = bufferedWriter.readLine()) != null) {
                arraylist.add(line);
            }
            bufferedWriter.close();
            is.close();
            con.disconnect();

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection" + e.toString());
        }
        for (int i = 0; i < objects.size(); i++)
            if (objects.get(i).toString().equals(arraylist.get(i).toString()))
                marks++;


        requestQueue = Volley.newRequestQueue(check.this);
        StringRequest request = new StringRequest(Request.Method.POST, "http://"+Ip_Address.ip_address+"/insert.php", new Response.Listener<String>() {

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
                parameters.put("id", usr_id);
                parameters.put("sub", sub_name);
                parameters.put("test", test);
                parameters.put("res", Integer.toString(marks));
                return parameters;
            }
        };
        requestQueue.add(request);
        Intent intent = new Intent(check.this, profile1.class);
        intent.putExtra("id", usr_id);
        startActivity(intent);
        finish();

    }


}