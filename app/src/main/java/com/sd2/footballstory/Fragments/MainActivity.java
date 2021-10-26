package com.sd2.footballstory.Fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.sd2.footballstory.R;
import com.sd2.footballstory.Resource.APIConnect;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button btnPlay;
    PlayerView playerView;
    boolean fullScreen=false;
    String m3u8Link,m3u8;
    ProgressBar progressBar;
    protected SimpleExoPlayer simpleExoPlayer;
    Thread objThread;
    Runnable objRunnable;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        Log.d("TAG", "onCreate: Created");
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.btnplay);
        playerView = findViewById(R.id.exoPlayer);
        progressBar = findViewById(R.id.progressBarVideo);
        imageView = findViewById(R.id.exo_fullscreen_icon);
//        mediaController = new MediaController(MainActivity.this);
        Intent intent = getIntent();
        m3u8 = intent.getStringExtra("m3u8");
//      m3u8 = "https://firstbaptus-vh.akamaihd.net/i/FBC_Dallas/363/371/Living_A_LIfe_That_Matters_47_,720pHigh,720pLow,960p,640p,480p,360p,.mp4.csmil/index_4_av.m3u8";
//        playVideo(m3u8);
//        getM3u8();
        btnPlay.setOnClickListener(v -> {
            simpleExoPlayer.stop(true);
            simpleExoPlayer.release();
            simpleExoPlayer = null;
            progressBar.setVisibility(View.VISIBLE);
            getM3u8();
//                playVideo(m3u8);
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientation();
            }
        });
    }

    @Override
    protected void onStop() {
        Log.d("TAG", "onStop: Exited from the viewers activity.");
        simpleExoPlayer.stop(true);
        simpleExoPlayer.release();
        super.onStop();
    }

    @Override
    protected void onStart() {
        simpleExoPlayer = null;
        getM3u8();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            fullScreen = false;
        }else{
            simpleExoPlayer.stop(true);
            simpleExoPlayer.release();
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            btnPlay.setVisibility(View.GONE);
            Objects.requireNonNull(getSupportActionBar()).hide();
        }else{
            btnPlay.setVisibility(View.VISIBLE);
            Objects.requireNonNull(getSupportActionBar()).show();
        }
    }
    private void orientation(){
        if(fullScreen){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            Toast.makeText(MainActivity.this, "Full Screen Off", Toast.LENGTH_SHORT).show();
            fullScreen = false;
        }else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Toast.makeText(MainActivity.this, "Full Screen", Toast.LENGTH_SHORT).show();
            fullScreen = true;
        }
    }
    private void getM3u8() {
        objRunnable = () -> {
            Looper.prepare();
            APIConnect apiConnect = new APIConnect(MainActivity.this);
            m3u8Link = apiConnect.m3u8Link(m3u8);
            objHandler.sendEmptyMessage(0);
        };

        objThread = new Thread(objRunnable);
        objThread.start();
    }
    Handler objHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                Log.d("TAG", "handleMessage: "+m3u8Link);
                //m3u8Link = "https://ktvhdnpicc.ekantipur.com/ktv_desktop_02347834/hd/kantipurtv/hd_1080/chunks.m3u8";
                TrackSelector trackSelector = new DefaultTrackSelector(MainActivity.this);
                simpleExoPlayer = new SimpleExoPlayer.Builder(MainActivity.this).setMediaSourceFactory(
                        new DefaultMediaSourceFactory(MainActivity.this)).setTrackSelector(trackSelector)
                        .build();
                playerView.setPlayer(simpleExoPlayer);
                // Build the media item.
                MediaItem mediaItem =
                        new MediaItem.Builder()
                                .setUri(m3u8Link)
                                .build();
                // Set the media item to be played.
                simpleExoPlayer.setMediaItem(mediaItem);
                // Prepare the player.
                simpleExoPlayer.prepare();
                // Start the playback.
                simpleExoPlayer.play();
                simpleExoPlayer.addListener(new Player.EventListener() {
                    @Override
                    public void onPlaybackStateChanged(int state) {
                        if (state == Player.STATE_READY) {
                            progressBar.setVisibility(View.GONE);
                            //Log.d("TAG", "onPlaybackStateChanged: " + state);

                        } else if (state == Player.STATE_BUFFERING) {
                            progressBar.setVisibility(View.VISIBLE);
                            //Log.d("TAG", "onPlaybackStateChanged: " + state);
                        }
                    }
                    @Override
                    public void onPlayerError(@NonNull ExoPlaybackException error)
                    {
                        if(error.type == ExoPlaybackException.TYPE_SOURCE){
                            Toast.makeText(MainActivity.this,"Server Not Responding",Toast.LENGTH_LONG).show();
                        }
//                        simpleExoPlayer.stop(true);
                        simpleExoPlayer.seekToDefaultPosition();
                        simpleExoPlayer.prepare();
                    }

                });
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    };

}