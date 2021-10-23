package com.sd2.footballstory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.sd2.footballstory.Adapter.RecyclerWebView;
import com.sd2.footballstory.Resource.VidConnect;

import java.util.LinkedHashMap;

public class HighlightsPage extends AppCompatActivity {
    RecyclerView recyclerView;
    LinkedHashMap<String,String> highlights = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlights_page);
        recyclerView = findViewById(R.id.recyclerViewWeb);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        highlights = new VidConnect(this).getResponse();
        RecyclerWebView recyclerWebView = new RecyclerWebView(this,highlights);
        recyclerView.setAdapter(recyclerWebView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
}