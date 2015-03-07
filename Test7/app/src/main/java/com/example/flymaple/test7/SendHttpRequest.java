package com.example.flymaple.test7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flymaple on 7/3/15.
 */
public class SendHttpRequest extends ActionBarActivity {
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        for(int i =0; i<=intent.getIntExtra("size",-1);i++){
            pairs.add(new BasicNameValuePair(String.valueOf(i), String.valueOf(intent.getIntExtra(String.valueOf(i),-1))));
        }
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("192.168.0.1");
            //
            httppost.setEntity(new UrlEncodedFormEntity(pairs));
            httpclient.execute(httppost);
        }catch (Exception e){}
    }

}
