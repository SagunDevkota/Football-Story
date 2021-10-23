package com.sd2.footballstory.Resource;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class VidConnect {
    Context context;
    private String line="";
    public JSONObject jObject;
    private LinkedHashMap<String,String> highlights = new LinkedHashMap<>();
    public VidConnect(Context context){
        this.context = context;
    }
    public LinkedHashMap<String,String> getResponse() {
        URL url = null;
        try {
            url = new URL("https://www.scorebat.com/video-api/v3/");
        } catch (Exception e) {
            Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();
        }
        HttpURLConnection http = null;
        try {
            assert url != null;
            http = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            Toast.makeText(context, "HTTPConnection Error", Toast.LENGTH_LONG).show();
        }
        assert http != null;
        http.setRequestProperty("Accept", "application/json");

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) http.getContent()));
            while (true) {
                String temp = in.readLine();
                if (temp == null) {
                    break;
                }
                line += temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            jObject = new JSONObject(line);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TAG", "getResponse: "+jObject);
        String embed="";
        try {
            JSONArray response = jObject.getJSONArray("response");
            for (int i=0;i<response.length();i++) {
                JSONObject innerObj = (JSONObject) response.get(i);
                JSONObject innerArray = (JSONObject) innerObj.getJSONArray("videos").get(0);
                Log.d("TAG", "getResponse: text "+innerObj.getString("title")+" "+innerArray.getString("embed"));
                highlights.put(innerObj.getString("title"),innerArray.getString("embed"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return highlights;
    };
}
