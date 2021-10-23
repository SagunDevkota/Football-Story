package com.sd2.footballstory.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sd2.footballstory.MainActivity;
import com.sd2.footballstory.R;
import com.sd2.footballstory.Resource.VidConnect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RecyclerWebView extends RecyclerView.Adapter<RecyclerWebView.ViewHolder> {
    Context context;
    LinkedHashMap<String,String> highlights = new LinkedHashMap<>();
    private boolean fullScreen = false;

    public RecyclerWebView(Context context,LinkedHashMap<String,String> highlights) {
        this.context = context;
        this.highlights = highlights;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_web_view,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText((new ArrayList<>(highlights.keySet())).get(holder.getAdapterPosition()));

        holder.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        WebSettings webSettings = holder.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        holder.webView.loadData((new ArrayList<>(highlights.values())).get(holder.getAdapterPosition()), "text/html", "utf-8");
    }


    @Override
    public int getItemCount() {
        return highlights.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        WebView webView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.webView);
            textView = itemView.findViewById(R.id.textViewWeb);
        }
    }
}
