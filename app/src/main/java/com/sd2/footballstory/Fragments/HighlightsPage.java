package com.sd2.footballstory.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sd2.footballstory.Adapter.RecyclerWebView;
import com.sd2.footballstory.R;
import com.sd2.footballstory.Resource.VidConnect;

import java.util.LinkedHashMap;

public class HighlightsPage extends Fragment {
    RecyclerView recyclerView;
    LinkedHashMap<String,String> highlights = new LinkedHashMap<>();
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    public static HighlightsPage newInstance() {
        return new HighlightsPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highlights_page,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewWeb);
        progressBar = view.findViewById(R.id.progressBarWebView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutWeb);
        progressBar.setVisibility(View.VISIBLE);
        load_highlights();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            highlights = null;
            load_highlights();
        });

        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void load_highlights(){
        Runnable runnable = () -> {
            highlights = new VidConnect(getActivity()).getResponse();
            Log.d("TAG", "load_highlights: "+highlights);
            if(highlights.containsKey("error")){
                objHandler.sendEmptyMessage(1);
            }else {
                objHandler.sendEmptyMessage(0);
            }
        };
        Thread objThread = new Thread(runnable);
        objThread.start();
    }
    Handler objHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            if(msg.what == 0) {
                recyclerView.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Loading Completed", Toast.LENGTH_SHORT).show();
                RecyclerWebView recyclerWebView = new RecyclerWebView(getActivity(), highlights);
                recyclerView.setAdapter(recyclerWebView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }else{
                Toast.makeText(getActivity(), highlights.get("error"), Toast.LENGTH_SHORT).show();
            }
        }
    };
}