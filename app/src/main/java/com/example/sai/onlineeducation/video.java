package com.example.sai.onlineeducation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class video extends Activity {
    VideoView videoView;
    ProgressDialog progressDialog;
    String sub_name,topic_name,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        sub_name = bundle.getString("sub_name");
        title = bundle.getString("title");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = (VideoView) findViewById(R.id.videoView);
        progressDialog = new ProgressDialog(video.this);
        progressDialog.setTitle("Video Streaming");
        progressDialog.setMessage("Buffering....");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        try{
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            Uri video = Uri.parse("http://"+Ip_Address.ip_address+"/"+sub_name+"/"+title+".mp4");
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);
        }
        catch(Exception e)
        {
            progressDialog.dismiss();
            Log.i("error", e.toString());
        }
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                progressDialog.dismiss();
                videoView.start();
            }
        });


    }
}
