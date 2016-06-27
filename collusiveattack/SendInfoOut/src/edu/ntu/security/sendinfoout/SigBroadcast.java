package edu.ntu.security.sendinfoout;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SigBroadcast extends BroadcastReceiver {
	private static final String targetUrl = "http://example.com";
	
	public SigBroadcast() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("SigBroadcast", "Receive a broadcast");
		context.startService(intent);

		
	}
}
