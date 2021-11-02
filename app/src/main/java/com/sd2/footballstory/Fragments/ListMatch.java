package com.sd2.footballstory.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sd2.footballstory.Adapter.RecyclerAdapterListMatch;
import com.sd2.footballstory.R;
import com.sd2.footballstory.Resource.APIConnect;

import java.util.ArrayList;

public class ListMatch extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> arrayList;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    Button button;
    public static ListMatch newInstance(){
        return new ListMatch();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_match,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        progressBar = view.findViewById(R.id.progressBarList);
        button = view.findViewById(R.id.btnListMatch);
        progressBar.setVisibility(View.GONE);
        button.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            getMatchInfo();
            button.setVisibility(View.GONE);
        });


        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            try {
                button.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                RearrangeItems();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void getMatchInfo() {
        Runnable objRunnable = () -> {
            Looper.prepare();
            APIConnect apiConnect = new APIConnect(getActivity());
            try {
                arrayList = apiConnect.all_matches_name();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(arrayList.get(0).equals("JSON Decoding Error") || arrayList.get(0).equals("Connection Error")){
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
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            if(msg.what == 0) {
                recyclerView.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Match Loading Completed", Toast.LENGTH_LONG).show();
                RecyclerAdapterListMatch recyclerAdapter = new RecyclerAdapterListMatch(arrayList, getActivity());
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }
    };

    private void RearrangeItems() throws InterruptedException {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getMatchInfo();
    }
}