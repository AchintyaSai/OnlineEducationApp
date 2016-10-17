package com.example.achintyasai.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Achintya Sai on 10/17/2016.
 */

public class PostingUsingVolleyLibrary  {
    private Context context;

//save the context recievied via constructor in a local variable

    public PostingUsingVolleyLibrary(Context context) {
        this.context = context;
    }
    public boolean postToweb(final String un, final String email, final String pass, final String phnum, final String subject, final String role, final String lat, final String lng)
    {
        final boolean[] returnvalue = {false};
        RequestQueue requestQueue;
        requestQueue =  VolleyServices.getInstance(this.context).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, IpAddress.ip_address+"/insert.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("success", "success");
                returnvalue[0] = true;
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
                parameters.put("user_name",un );
                parameters.put("password",pass);
                parameters.put("address",email);
                parameters.put("phnum", phnum);
                parameters.put("subject", subject);
                parameters.put("lat",lat);
                parameters.put("lng", lng);
                parameters.put("role", role);
                return parameters;
            }
        };
        requestQueue.add(request);

        return returnvalue[0];
    }
}
