package com.sd2.footballstory.Resource;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class APIConnect {
    public Context context;
    public ArrayList<String> matchName = new ArrayList<>();
    public JSONObject jObject;
    public ArrayList<String> stream_links = new ArrayList<>();
    private String line="";
    private HttpURLConnection http=null;

    public APIConnect(Context context) {
        this.context = context;
    }

    public ArrayList<String> all_matches_name() throws InterruptedException {
        line = common_task("/all_matches_name");
        try {
            jObject = new JSONObject(line);

            Iterator<String> keys = jObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                matchName.add(key);
            }
        } catch (JSONException e) {
            Toast.makeText(context, "IO Error", Toast.LENGTH_LONG).show();
        }
        http.disconnect();

        return matchName;
    }

    public ArrayList<String> stream_link(String match_name) {
        line = common_task("/selected_match/"+match_name);
        Log.d("TAG", "stream_link: "+match_name);
        int counter = 0;
        try {
            jObject = new JSONObject(line);
            while (true) {
                if (jObject.has(String.valueOf(counter))) {
                    stream_links.add(jObject.getString(String.valueOf(counter)));
                    counter++;
                } else {
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        http.disconnect();
        return stream_links;
    }

    public String m3u8Link(String link) {
        line = common_task("/soccermotor/"+link);
        return line;
    }

    private String common_task(String link_argument) {
        URL url = null;
        try {
            String baseURI = "http://0444-34-91-239-242.ngrok.io/";
            url = new URL(baseURI + link_argument);
        } catch (Exception e) {
            Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();
        }
        http = null;
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
        return line;
    }
}

