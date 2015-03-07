package com.example.flymaple.test3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

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
public class IncomingSms extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent){
        final Bundle bundle = intent.getExtras();
        SmsMessage currentMessage = null;
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                } // end for loop
            } // bundle is null
        } catch (Exception e) {
        }

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("192.168.0.1");
            //HttpResponse resp = httpclient.execute(httppost);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("key1", currentMessage.toString()));
            pairs.add(new BasicNameValuePair("key2", "value2"));
            httppost.setEntity(new UrlEncodedFormEntity(pairs));
        }catch (Exception e){}
    }
}
