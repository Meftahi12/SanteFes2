package com.navigation.drawer.activity.tools;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Pro on 19/02/2017.
 */

public class HttpManager {

    public static String getData(String url){
        AndroidHttpClient client = AndroidHttpClient.newInstance("AndroidAgent");
        HttpGet request = new HttpGet(url);
        HttpResponse response;

        try{
            response = client.execute(request);
            return org.apache.http.util.EntityUtils.toString(response.getEntity());
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            client.close();
        }
    }

    public static String getDatas(String url){
        BufferedReader br  ;
        try{
            URL Url = new URL(url);
            HttpURLConnection htc = (HttpURLConnection) Url.openConnection();
            htc.setConnectTimeout(1000*200);
            br = new BufferedReader(new InputStreamReader(htc.getInputStream()));

            StringBuilder sb = new StringBuilder();

            String line ;
            while( (line = br.readLine())!= null){
                sb.append(line + '\n');
            }
            return sb.toString();
        }
        catch(Exception e){
            return null ;
        }


    }
}
