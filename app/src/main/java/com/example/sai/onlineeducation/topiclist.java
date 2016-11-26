package com.example.sai.onlineeducation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class topiclist extends AppCompatActivity {
    String sub_name,usr_id;
    ImageView subject;
    ListView lv;
    Button b1 ;
    Space sp ;
    LinearLayout linearLayout;
    AlertDialog.Builder alertDialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Topic List");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topiclist);
        Bundle bundle = getIntent().getExtras();
        usr_id = bundle.getString("usr_id");
        sub_name=bundle.getString("sub_name");
        subject = (ImageView)findViewById(R.id.subject);
        alertDialogBuilder = new AlertDialog.Builder(this);
        sp = new Space(this);
        sp.setLayoutParams(new FrameLayout.LayoutParams(200,200));
        linearLayout = (LinearLayout)findViewById(R.id.ll);
        b1 = new Button(this);
        b1.setText("Take test");
        lv=(ListView)findViewById(R.id.lv);
        ArrayList<String> arraylist=new ArrayList<String>();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,android.R.id.text1,arraylist);
        lv.setAdapter(adapter);
        linearLayout.addView(sp);
        linearLayout.addView(b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(topiclist.this, test1.class);
                intent.putExtra("usr_id",usr_id);
                intent.putExtra("sub_name",sub_name);
                startActivity(intent);

            }
        });
        b1.setBackgroundColor(Color.parseColor("#ff009688"));
        b1.setTextColor(Color.WHITE);

        try{
            URL url = new URL("http://"+Ip_Address.ip_address+"/images/"+sub_name+".png");
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            subject.setImageBitmap(bmp);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection con = (HttpURLConnection) ( new URL("http://"+Ip_Address.ip_address+"/topiclist.php?sub_name="+sub_name)).openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            InputStream is = con.getInputStream();
            BufferedReader bufferedWriter=new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
            String response="";
            String line="";
            while((line=bufferedWriter.readLine())!=null)
            {
                response=line;
                arraylist.add(response);

            }
            adapter.notifyDataSetChanged();

            bufferedWriter.close();
            is.close();
            con.disconnect();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                   final String s = ((String) lv.getItemAtPosition(arg2));
                    alertDialogBuilder.setMessage("Would you like to download "+s+".pdf for your reference");
                    alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                                String urlview ="http://"+Ip_Address.ip_address+"/filedownload.php?file="+s+".pdf&sub="+sub_name;
                            Uri uri = Uri.parse(urlview);
                            DownloadManager.Request r = new DownloadManager.Request(uri);
                            r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, s+".pdf");
                            r.allowScanningByMediaScanner();
                            r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            dm.enqueue(r);
                            Intent intent = new Intent(topiclist.this, video.class);
                            intent.putExtra("title",s );
                            intent.putExtra("sub_name",sub_name);
                            startActivity(intent);
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            Intent intent = new Intent(topiclist.this, video.class);
                            intent.putExtra("title",s );
                            intent.putExtra("sub_name",sub_name);
                            startActivity(intent);
                        }
                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();





                }
            });



        }catch(Exception e){
            Log.e("log_tag", "Error in http connection" + e.toString());
        }
    }


}
