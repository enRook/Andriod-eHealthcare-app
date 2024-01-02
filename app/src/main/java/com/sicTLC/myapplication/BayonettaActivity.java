package com.sicTLC.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class BayonettaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayonetta);

        VideoView videoView = findViewById(R.id.videoView);

        // Set the path to your video file
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.bayonetta;

        // Set the video URI
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // Start video playback
        videoView.start();
    }
}
