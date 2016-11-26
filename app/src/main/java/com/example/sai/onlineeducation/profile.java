package com.example.sai.onlineeducation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class profile extends Activity {
    TextView name;
    TextView email,protitle,learningNowtv,relsubtv,performancetv;
    LinearLayout learningNow,relsub,lnsvll,rssvll;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Profile");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        Bundle bundle = getIntent().getExtras();
        userid=bundle.getString("id");
        protitle=new TextView(this);
        protitle.setText("Profile Info");
        name = new TextView(this);
        email = new TextView(this);
        learningNowtv=new TextView(this);
        relsubtv=new TextView(this);
        performancetv = new TextView(this);
        learningNowtv.setText("Learning Now");
        relsubtv.setText("Related Subjects");
        performancetv.setText("Performace");
        NestedScrollView sv = (NestedScrollView)findViewById(R.id.nes);
        HorizontalScrollView learningNowsv = new HorizontalScrollView(this);
        HorizontalScrollView relsubsv = new HorizontalScrollView(this);
        lnsvll = new LinearLayout(this);
        rssvll = new LinearLayout(this);
        LinearLayout ll1=new LinearLayout(this);
        LinearLayout ll2=new LinearLayout(this);
        learningNow=new LinearLayout(this);
        learningNow.setOrientation(LinearLayout.HORIZONTAL);
        relsub=new LinearLayout(this);
        relsub.setOrientation(LinearLayout.HORIZONTAL);
        ll2.setOrientation(LinearLayout.VERTICAL);
        ll2.setBackgroundColor(Color.WHITE);
        LinearLayout ll = new LinearLayout(this);
        ll1.setOrientation(LinearLayout.VERTICAL);

        ll.setX(25);
        connection1("http://"+Ip_Address.ip_address+"/profile1.php?usr_id="+userid);
        connection2("http://"+Ip_Address.ip_address+"/listselect.php?id="+userid);
        connection3("http://"+Ip_Address.ip_address+"/relsub.php?id="+userid);
        ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(50,50,50,50);

        ll.setLayoutParams(new FrameLayout.LayoutParams(900, 400));

        ll.setBackgroundColor(Color.WHITE);
        ll.setScrollContainer(true);
        ll.addView(name);
        ll.addView(email);


        learningNowsv.addView(lnsvll);
        relsubsv.addView(rssvll);

        ll2.addView(ll);

        learningNow.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,400));
        learningNow.setX(10);
        learningNow.setBackgroundColor(Color.WHITE);
        learningNow.addView(learningNowsv);

        relsub.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,400));
        relsub.setScrollContainer(true);
        relsub.setX(10);
        relsub.setBackgroundColor(Color.WHITE);
        relsub.addView(relsubsv);

        ll2.addView(learningNowtv);
        ll2.addView(learningNow);
        ll2.addView(relsubtv);
        ll2.addView(relsub);
        ll2.addView(performancetv);
        try{
            ArrayList<String> list1 = new ArrayList<String>();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection con = (HttpURLConnection) (new URL("http://"+Ip_Address.ip_address+"/showStudentPerformance.php?&id="+userid)).openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            InputStream is = con.getInputStream();
            BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            String line = "";
            String line1="";
            list1.add(0,"sample");
            int i=1;
            while ((line = bufferedWriter.readLine()) != null) {

                list1.add(i,line);
                i++;
            }


            ArrayList<String> list2 = new ArrayList<String>();
            StrictMode.ThreadPolicy policy2 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy2);
            HttpURLConnection con1 = (HttpURLConnection) (new URL("http://"+Ip_Address.ip_address+"/showStudentPerformance1.php?&id="+userid)).openConnection();
            con1.setRequestMethod("POST");
            con1.setDoInput(true);
            con1.setDoOutput(true);
            con1.connect();
            InputStream is1 = con1.getInputStream();
            BufferedReader bufferedWriter2 = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"));
            String line2 = "";
            String line3="";
            list2.add(0,"sample");
            int l=1;
            while ((line2 = bufferedWriter2.readLine()) != null) {

                list2.add(l,line2);
                l++;
            }




            int temp =(list1.size()-1);
            Log.i("temp",Integer.toString(temp));
            LinearLayout performance1[] = new LinearLayout[temp+1];
            TextView tv12[] = new TextView[temp+1];

            ImageView iv[] =new ImageView[temp+1];
            Space spaces[] = new Space[temp+1];
            for(int j=1;j<=temp;j++) {
                spaces[j] = new Space(this);
                iv[j] = new ImageView(this);
                iv[j].setLayoutParams(new FrameLayout.LayoutParams(200,200));
                tv12[j] = new TextView(this);
                if(Integer.parseInt(list2.get(j))<=20) {
                    iv[j].setBackgroundResource(R.drawable.rookie);
                    tv12[j].setText("Subject : " + list1.get(j) + " \nPercentage : " + list2.get(j) + "%\nDesignation : Rookie");
                }
                else if(Integer.parseInt(list2.get(j))<=40) {
                    iv[j].setBackgroundResource(R.drawable.pro);
                    tv12[j].setText("Subject : " + list1.get(j) + " \nPercentage : " + list2.get(j) + "%\nDesignation : Pro");
                }
                else if(Integer.parseInt(list2.get(j))<=60) {
                    iv[j].setBackgroundResource(R.drawable.royal);
                    tv12[j].setText("Subject : " + list1.get(j) + " \nPercentage : " + list2.get(j) + "%\nDesignation : Royal");
                }
                else if(Integer.parseInt(list2.get(j))<=90) {
                    iv[j].setBackgroundResource(R.drawable.master);
                    tv12[j].setText("Subject : "+list1.get(j)+" \nPercentage : "+list2.get(j)+"%\nDesignation : Master");
                }
                else {
                    iv[j].setBackgroundResource(R.drawable.legend);
                    tv12[j].setText("Subject : "+list1.get(j)+" \nPercentage : "+list2.get(j)+"%\nDesignation : Legend");
                }
                iv[j].setPadding(20,20,20,20);
                spaces[j].setLayoutParams(new FrameLayout.LayoutParams(10,10));


                performance1[j] = new LinearLayout(this);
                performance1[j].setOrientation(LinearLayout.HORIZONTAL);
                performance1[j].setLayoutParams(new FrameLayout.LayoutParams(900, 400));
                performance1[j].setPadding(50,50,50,50);
                performance1[j].setBackgroundColor(Color.TRANSPARENT);
                performance1[j].addView(iv[j]);
                performance1[j].addView(tv12[j]);
                performance1[j].setX(10);

                ll2.addView(performance1[j]);
                ll2.addView(spaces[j]);

            }
            bufferedWriter.close();
            is.close();
            con.disconnect();
        }catch(Exception e)
        {

        }


        sv.addView(ll2);


    }
    public void connection1(String str)
    {
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection con = (HttpURLConnection) ( new URL(str)).openConnection();
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
            name.setText("NAME:"+list.get(count-2));
            email.setText("EMAIL:"+list.get(count-1));
            bufferedWriter.close();
            is.close();
            con.disconnect();


        }catch(Exception e){
            Log.e("log_tag", "Error in http connection" + e.toString());
        }
    }
    public void connection2(String str)
    {

        try{

            ArrayList<String>arrayList= new ArrayList<String>();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection con = (HttpURLConnection) ( new URL(str)).openConnection();
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
                arrayList.add(response);
            }
            final ImageButton[] imgbtn = new ImageButton[arrayList.size()];

            for(int i =0;i<arrayList.size();i++)
            {
                final String urlstr="http://"+Ip_Address.ip_address+"/images/"+arrayList.get(i)+".png";
                URL url = new URL(urlstr);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                imgbtn[i] = new ImageButton(this);
                imgbtn[i].setTag(arrayList.get(i));
                imgbtn[i].setImageBitmap(bmp);
                imgbtn[i].setLayoutParams(new FrameLayout.LayoutParams(300,300));
                final int finalI = i;
                imgbtn[finalI].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s=imgbtn[finalI].getTag().toString();
                        Intent intent = new Intent(profile.this, topiclist.class);
                        intent.putExtra("usr_id",userid);
                        intent.putExtra("sub_name",s);
                        startActivity(intent);
                    }
                });

                lnsvll.addView(imgbtn[i]);

            }
            bufferedWriter.close();
            is.close();
            con.disconnect();



        }catch(Exception e){
            Log.e("log_tag", "Error in http connection"+e.toString());
        }
    }
    public void connection3(String str)
    {
        try{


            ArrayList<String>arrayList= new ArrayList<String>();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection con = (HttpURLConnection) ( new URL(str)).openConnection();
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
                arrayList.add(response);
            }
            final ImageButton[] imgbtn = new ImageButton[arrayList.size()];
            for(int y=0;y<arrayList.size();y++)
                Log.i(y+"",arrayList.get(y));

            for(int i =0;i<arrayList.size();i++)
            {
                URL url = new URL("http://"+Ip_Address.ip_address+"/images/"+arrayList.get(i)+".png");
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                imgbtn[i] = new ImageButton(this);
                imgbtn[i].setImageBitmap(bmp);
                imgbtn[i].setTag(arrayList.get(i));
                imgbtn[i].setLayoutParams(new FrameLayout.LayoutParams(300,300));
                final int finalI = i;

                imgbtn[finalI].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s=imgbtn[finalI].getTag().toString();
                        Intent intent = new Intent(profile.this, courseRegistration.class);
                        intent.putExtra("sub_name",s);
                        intent.putExtra("userid",userid);
                        startActivity(intent);
                        finish();
                    }
                });

                rssvll.addView(imgbtn[i]);

            }
            bufferedWriter.close();
            is.close();
            con.disconnect();



        }catch(Exception e){
            Log.e("log_tag", "Error in http connection"+e.toString());
        }
    }


}

