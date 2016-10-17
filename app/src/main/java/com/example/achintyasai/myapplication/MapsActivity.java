package com.example.achintyasai.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.StrictMode;
import android.support.annotation.FloatRange;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String latitude;
    String longitude;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getString("latitude");
        longitude = bundle.getString("longitude");
        user_id = bundle.getString("user_id");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.i("lati",latitude);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList <String> lat = MapsActivity.getResults(IpAddress.ip_address+"/get_user_id.php?opt=lat&latitude="+latitude+"&longitude="+longitude);
        ArrayList <String> lng = MapsActivity.getResults(IpAddress.ip_address+"/get_user_id.php?opt=long&latitude="+latitude+"&longitude="+longitude);
        final ArrayList <String> dist = MapsActivity.getResults(IpAddress.ip_address+"/get_user_id.php?opt=dist&latitude="+latitude+"&longitude="+longitude);
        ArrayList <String> usrnm = MapsActivity.getResults(IpAddress.ip_address+"/get_user_id.php?opt=usrnm&latitude="+latitude+"&longitude="+longitude);
        final ArrayList <String> usrid = MapsActivity.getResults(IpAddress.ip_address+"/get_user_id.php?opt=usrid&latitude="+latitude+"&longitude="+longitude);
        final ArrayList <String> phnum = MapsActivity.getResults(IpAddress.ip_address+"/get_user_id.php?opt=phnum&latitude="+latitude+"&longitude="+longitude);
        final ArrayList <String> subject = MapsActivity.getResults(IpAddress.ip_address+"/get_user_id.php?opt=subject&latitude="+latitude+"&longitude="+longitude);
        final ArrayList <String> role = MapsActivity.getResults(IpAddress.ip_address+"/get_user_id.php?opt=role&latitude="+latitude+"&longitude="+longitude);

        ArrayList<Marker> markers = new ArrayList<Marker>();
         final Map<Marker, String> allMarkersMap = new HashMap<Marker, String>();
        for(int j=0;j<lat.size();j++){
            final int finalk = j;

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
               if(user_id.equals(usrid.get(j))) {
                   continue;
               }
               else {
                   double lati = Double.parseDouble(lat.get(j));
                   double longi = Double.parseDouble(lng.get(j));
                   if(role.get(j).equals("teacher")) {
                       Marker marker = mMap.addMarker(new MarkerOptions()
                               .position(new LatLng(lati, longi))
                               .title("" + usrnm.get(j))
                               .snippet("Subject : " + subject.get(j) + "\nDistance : " + df.format(Double.parseDouble(dist.get(j))) + "km\nClick to contact")
                               .icon(BitmapDescriptorFactory.fromResource(R.drawable.teacher)));
                       allMarkersMap.put(marker, phnum.get(j));
                   }
                   else if(role.get(j).equals("student"))
                   {
                       Marker marker = mMap.addMarker(new MarkerOptions()
                               .position(new LatLng(lati, longi))
                               .title("" + usrnm.get(j))
                               .snippet("Subject : " + subject.get(j) + "\nDistance : " + df.format(Double.parseDouble(dist.get(j))) + "km\nClick to contact")
                               .icon(BitmapDescriptorFactory.fromResource(R.drawable.learner)));
                       allMarkersMap.put(marker, phnum.get(j));
                   }
                   final int finalJ1 = j;
                   Log.i("finalj",""+j);
                   mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                       @Override
                       public View getInfoWindow(Marker arg0) {
                           return null;
                       }

                       @Override
                       public View getInfoContents(Marker marker) {

                           Context context = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                           LinearLayout info = new LinearLayout(context);
                           info.setOrientation(LinearLayout.VERTICAL);

                           TextView title = new TextView(context);
                           title.setTextColor(Color.BLACK);
                           title.setGravity(Gravity.CENTER);
                           title.setTypeface(null, Typeface.BOLD);
                           title.setText(marker.getTitle());
                           title.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   Toast.makeText(getBaseContext(), "Hello world", Toast.LENGTH_SHORT).show();
                               }
                           });


                           TextView snippet = new TextView(context);
                           snippet.setTextColor(Color.GRAY);
                           snippet.setText(marker.getSnippet());

                           info.addView(title);
                           info.addView(snippet);

                           return info;
                       }
                   });
                   mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                       @Override
                       public void onInfoWindowClick(Marker marker) {
                           Uri uri = Uri.parse("smsto:"+allMarkersMap.get(marker));
                           Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                           intent.setPackage("com.whatsapp");
                           startActivity(Intent.createChooser(intent,""));
                       }
                   });
               }
        }
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
                .radius(1000)
                .fillColor(0x95bbccdd)
                .strokeWidth(5)
                .strokeColor(0x10000000)

        );

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
