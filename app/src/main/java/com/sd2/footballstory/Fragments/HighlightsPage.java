package com.sd2.footballstory.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sd2.footballstory.Adapter.RecyclerWebView;
import com.sd2.footballstory.R;
import com.sd2.footballstory.Resource.VidConnect;

import java.util.LinkedHashMap;

public class HighlightsPage extends Fragment {
    RecyclerView recyclerView;
    LinkedHashMap<String,String> highlights = new LinkedHashMap<>();

    public static HighlightsPage newInstance() {
        return new HighlightsPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highlights_page,container,false);
        recyclerView = view.findViewById(R.id.recyclerViewWeb);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        highlights = new VidConnect(getActivity()).getResponse();
        RecyclerWebView recyclerWebView = new RecyclerWebView(getActivity(),highlights);
        recyclerView.setAdapter(recyclerWebView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}