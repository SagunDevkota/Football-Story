package com.sd2.footballstory.Resource;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;

public class VidConnect {
    Context context;
    private String line="";
    public JSONObject jObject;
    private final LinkedHashMap<String,String> highlights = new LinkedHashMap<>();
    public VidConnect(Context context){
        this.context = context;
    }
    public LinkedHashMap<String,String> getResponse() {
        URL url;
        try {
            url = new URL("https://www.scorebat.com/video-api/v3/");
        } catch (MalformedURLException e) {
            highlights.put("error","Malformed URL");
            return highlights;
        }
        HttpURLConnection http;

        try {
            http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Accept", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) http.getContent()));
            while (true) {
                String temp = in.readLine();
                if (temp == null) {
                    break;
                }
                line += temp;
            }
        } catch (IOException e) {
            highlights.put("error","Connection Error");
            return highlights;
        }
        try {
            jObject = new JSONObject(line);
            JSONArray response = jObject.getJSONArray("response");
            for (int i=0;i<response.length();i++) {
                JSONObject innerObj = (JSONObject) response.get(i);
                JSONObject innerArray = (JSONObject) innerObj.getJSONArray("videos").get(0);
                Log.d("TAG", "getResponse: text "+innerObj.getString("title")+" "+innerArray.getString("embed"));
                highlights.put(innerObj.getString("title"),innerArray.getString("embed"));
            }
        } catch (JSONException e) {
            highlights.put("error","Corrupted JSON");
            return highlights;
        }
        return highlights;
    }
}
