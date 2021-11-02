package com.sd2.footballstory.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sd2.footballstory.Adapter.RecyclerAdapterOption;
import com.sd2.footballstory.R;
import com.sd2.footballstory.Resource.APIConnect;

import java.util.ArrayList;

public class LinkOption extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> stream_links;
    SwipeRefreshLayout swipeRefreshLayout;
    String match_name;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_option);
        recyclerView = findViewById(R.id.recyclerViewOpt);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutOpt);
        progressBar = findViewById(R.id.progressBarOpt);

        Intent intent = getIntent();
        stream_links = intent.getStringArrayListExtra("stream_links");
        match_name = intent.getStringExtra("match_name");

        Toast.makeText(LinkOption.this, "Loading Links", Toast.LENGTH_LONG).show();
        getStreamLinks();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            RearrangeItems();
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void RearrangeItems() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        getStreamLinks();
        RecyclerAdapterOption recyclerAdapter = new RecyclerAdapterOption(stream_links,LinkOption.this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(LinkOption.this));
    }

    private void getStreamLinks() {
        Runnable objRunnable = () -> {
            stream_links = new APIConnect(LinkOption.this).stream_link(match_name);
            if(stream_links.contains("JSON Decoding Error") || stream_links.contains("Connection Error")){
                Toast.makeText(LinkOption.this,""+stream_links.get(0),Toast.LENGTH_LONG).show();
                objHandler.sendEmptyMessage(1);
            }else {
                objHandler.sendEmptyMessage(0);
            }
        };

        Thread objThread = new Thread(objRunnable);
        objThread.start();
    }
    Handler objHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Toast.makeText(LinkOption.this, "Loading Completed", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
            if(msg.what == 0) {
                RecyclerAdapterOption recyclerAdapter = new RecyclerAdapterOption(stream_links, LinkOption.this);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(LinkOption.this));
            }
        }
    };
}