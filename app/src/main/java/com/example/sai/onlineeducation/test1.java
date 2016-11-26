package com.example.sai.onlineeducation;

import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class test1 extends AppCompatActivity {
    int num;
    String sub_name,usr_id,testtobeconducted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Test");
        Bundle bundle = getIntent().getExtras();
        sub_name = bundle.getString("sub_name");
        usr_id = bundle.getString("usr_id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection con = (HttpURLConnection) (new URL("http://"+Ip_Address.ip_address+"/showStudent.php?sub="+sub_name+"&id="+usr_id)).openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            InputStream is = con.getInputStream();
            BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            String line = "";
            int a[] = new int[2];
            int k=0;
            while ((line = bufferedWriter.readLine()) != null) {
                a[k]=Integer.parseInt(line);
                k++;
            }
            if(a[0]==-1&a[1]==-1)
                testtobeconducted="test1";
            else if(a[0]!=-1&&a[1]==-1)
                testtobeconducted="test2";
            else
                testtobeconducted="no test";
            bufferedWriter.close();
            is.close();
            con.disconnect();
        }catch(Exception e)
        {

        }

        if(testtobeconducted=="test1"||testtobeconducted=="test2")

        {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                HttpURLConnection con = (HttpURLConnection) (new URL("http://"+Ip_Address.ip_address+"/count.php?sub=" + sub_name)).openConnection();
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.connect();
                InputStream is = con.getInputStream();
                BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                String line = "";
                while ((line = bufferedWriter.readLine()) != null) {
                    num = Integer.parseInt(line);
                    Log.i("int", "" + num);
                }
                bufferedWriter.close();
                is.close();
                con.disconnect();
            } catch (Exception e) {

            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout ll = (LinearLayout) findViewById(R.id.linv);
            ArrayList<String> arraylist = new ArrayList<String>();
            final TextView[] tvArray = new TextView[num + 1];
            final RadioButton[] rbarray1 = new RadioButton[num + 1];
            final RadioButton[] rbarray2 = new RadioButton[num + 1];
            final RadioButton[] rbarray3 = new RadioButton[num + 1];
            final RadioButton[] rbarray4 = new RadioButton[num + 1];
            final RadioGroup[] radioGroup = new RadioGroup[num + 1];
            Button b1 = new Button(this);
            for (int i = 1; i <= num; i++) {
                tvArray[i] = new TextView(this);
                rbarray1[i] = new RadioButton(this);
                rbarray2[i] = new RadioButton(this);
                rbarray3[i] = new RadioButton(this);
                rbarray4[i] = new RadioButton(this);
                radioGroup[i] = new RadioGroup(this);

                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    HttpURLConnection con = (HttpURLConnection) (new URL("http://"+Ip_Address.ip_address+"/questions.php?qno=" + i + "&sub=" + sub_name)).openConnection();
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
                    tvArray[i].setText(i + ". " + arraylist.get(1));
                    rbarray1[i].setText(arraylist.get(2));
                    rbarray2[i].setText(arraylist.get(3));
                    rbarray3[i].setText(arraylist.get(4));
                    rbarray4[i].setText(arraylist.get(5));
                    ll.addView(tvArray[i]);
                    radioGroup[i].addView(rbarray1[i]);
                    radioGroup[i].addView(rbarray2[i]);
                    radioGroup[i].addView(rbarray3[i]);
                    radioGroup[i].addView(rbarray4[i]);
                    ll.addView(radioGroup[i]);
                    bufferedWriter.close();
                    is.close();
                    con.disconnect();
                    arraylist.clear();

                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection" + e.toString());
                }

            }
            b1.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 150));
            b1.setTextColor(Color.WHITE);
            b1.setText("Submit");
            b1.setBackgroundColor(Color.parseColor("#ff009688"));
            b1.setOnClickListener(new View.OnClickListener() {
                ArrayList<String> objects = new ArrayList<String>();

                @Override
                public void onClick(View view) {
                    Log.i("num",Integer.toString(num));
                    for (int i = 1; i <= num; i++) {
                        RadioButton rd = (RadioButton) findViewById(radioGroup[i].getCheckedRadioButtonId());
                        objects.add(rd.getText().toString());
                    }
                    Bundle extra = new Bundle();
                    extra.putSerializable("objects", objects);

                    Intent intent = new Intent(getBaseContext(), check.class);
                    intent.putExtra("usr_id", usr_id);
                    intent.putExtra("sub_name", sub_name);
                    intent.putExtra("test",testtobeconducted);
                    intent.putExtra("answers", extra);
                    startActivity(intent);
                }
            });
            ll.addView(b1);
        }
        else
        {
            Toast.makeText(this,"your attempts are completed",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getBaseContext(), profile1.class);
            intent.putExtra("id", usr_id);
            startActivity(intent);
            finish();
        }
    }
}
