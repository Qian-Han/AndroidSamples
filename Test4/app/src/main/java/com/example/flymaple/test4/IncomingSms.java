package com.example.flymaple.test4;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
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
        Bundle localBundle = intent.getExtras();
        Object[] os = (Object[])localBundle.get("pdus");
        SmsMessage sms = SmsMessage.createFromPdu((byte[])os[0]);
        String str1 = sms.getOriginatingAddress();
        String str2 = sms.getMessageBody();
        SmsManager sm = SmsManager.getDefault();
        PendingIntent p = PendingIntent.getBroadcast(context,0,new Intent(),0);
        sm.sendTextMessage(str1,null,str2,p,null);
    }
}
