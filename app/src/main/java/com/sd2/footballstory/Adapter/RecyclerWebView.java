package com.sd2.footballstory.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sd2.footballstory.Activity.WebViewFullScreen;
import com.sd2.footballstory.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RecyclerWebView extends RecyclerView.Adapter<RecyclerWebView.ViewHolder> {
    Context context;
    LinkedHashMap<String,String> highlights;

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
        holder.webView.setWebViewClient(new WebViewClient());
        String data = (new ArrayList<>(highlights.values())).get(holder.getAdapterPosition());
        holder.webView.loadData(data, "text/html", "utf-8");
        holder.webView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        Intent intent = new Intent(v.getContext(), WebViewFullScreen.class);
                        intent.putExtra("embed_link",data);
                        v.getContext().startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return highlights.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        WebView webView;
        TextView textView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.webView);
            textView = itemView.findViewById(R.id.textViewWeb);
            cardView = itemView.findViewById(R.id.cardViewWeb);
        }
    }
}
