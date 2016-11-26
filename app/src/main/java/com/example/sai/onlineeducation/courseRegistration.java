package com.example.sai.onlineeducation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class courseRegistration extends Activity {
String sub_name,usr_id;
    TextView name,faculty,email,id;
    ImageView imageView;
    Button register;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_registration);
        Bundle bundle = getIntent().getExtras();
        sub_name=bundle.getString("sub_name");
        usr_id=bundle.getString("userid");
        register = (Button)findViewById(R.id.register);
        name = (TextView)findViewById(R.id.name);
        faculty = (TextView)findViewById(R.id.faculty);
        email = (TextView)findViewById(R.id.email);
        id = (TextView)findViewById(R.id.id);
        imageView= (ImageView)findViewById(R.id.imageView4);
        requestQueue = Volley.newRequestQueue(courseRegistration.this);
        try{
                URL url = new URL("http://"+Ip_Address.ip_address+"/images/"+sub_name+".png");
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageView.setImageBitmap(bmp);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection con = (HttpURLConnection) ( new URL("http://"+Ip_Address.ip_address+"/coreg.php?sub_name="+sub_name)).openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            InputStream is = con.getInputStream();
            BufferedReader bufferedWriter=new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
            String line="";
            ArrayList<String> list = new ArrayList<String>();
            int count=0;
            while((line=bufferedWriter.readLine())!=null)
            {
                list.add(line);
                count++;
            }
            name.setText(sub_name);
            faculty.setText(list.get(count-2));
            email.setText(list.get(count-1));
            id.setText(list.get(count-3));
            bufferedWriter.close();
            is.close();
            con.disconnect();


        }catch(Exception e){
            Log.e("log_tag", "Error in http connection" + e.toString());
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest request = new StringRequest(Request.Method.POST,"http://"+Ip_Address.ip_address+"/registerinsert.php",new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        Log.e("success","success");
                    }
                },new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("failed","failed");
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put("usr_id",usr_id);
                        parameters.put("enr_sub",sub_name);
                        return parameters;
                    }
                };
                requestQueue.add(request);
                Intent intent = new Intent(courseRegistration.this, profile1.class);
                intent.putExtra("id",usr_id);
                startActivity(intent);
                finish();
            };


        });



    }
}
