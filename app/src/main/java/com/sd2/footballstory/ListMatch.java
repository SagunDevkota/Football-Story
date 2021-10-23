package com.sd2.footballstory;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sd2.footballstory.Adapter.RecyclerAdapter;
import com.sd2.footballstory.Resource.APIConnect;

import java.util.ArrayList;

public class ListMatch extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> arrayList;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_match);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        progressBar = findViewById(R.id.progressBarList);

        getMatchInfo();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            try {
                RearrangeItems();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void getMatchInfo() {
        Runnable objRunnable = () -> {
            Looper.prepare();
            APIConnect apiConnect = new APIConnect(ListMatch.this);
            try {
                arrayList = apiConnect.all_matches_name();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            objHandler.sendEmptyMessage(0);
        };
        Thread objThread = new Thread(objRunnable);
        objThread.start();
    }
    Handler objHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ListMatch.this, "Match Loading Completed", Toast.LENGTH_LONG).show();
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList,ListMatch.this);
            recyclerView.setAdapter(recyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ListMatch.this));
        }
    };

    private void RearrangeItems() throws InterruptedException {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getMatchInfo();
    }
}