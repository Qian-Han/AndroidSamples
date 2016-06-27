package edu.ntu.android.maas.client.clientapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pillar on 4/18/2016.
 */
public class Constants {

    public static final double APP_VERSION = 1.2;
    public static final String CURRENT_VERSION_URL = Utils.base64Decoding("aHR0cDovL3BhdC5zY2UubnR1LmVkdS5zZy9ob25leXBvdC93aGlzcGVyc192ZXJzaW9uLnBocA==");
    public static final String LATEST_APP_URL = Utils.base64Decoding("aHR0cDovL3BhdC5zY2UubnR1LmVkdS5zZy9ob25leXBvdC93aGlzcGVycy5hcGs=");

    public static MainActivity context;
    //private final static String INSTRUCTION_URL = "http://155.69.144.198:8080/maas/instructions.jsp"; //http://pat.sce.ntu.edu.sg/honeypot/classes/a.dex";//"http://pat.scse.ntu.edu.sg/android/instructions.jsp";
    private final static String INSTRUCTION_URL = Utils.base64Decoding("aHR0cDovLzE1NS42OS4xNTAuMjA0OjgwODAvbWFhcy9oZWFydGJlYXQ=");;
    //public final static String SERVICE_URL = "http://155.69.144.198:8080/maas/task.dex";
    public final static String FETCH_URL = Utils.base64Decoding("aHR0cDovL3BhdC5zY2UubnR1LmVkdS5zZy9ob25leXBvdC93aGlzcGVycy5waHA=");
    public final static String POST_URL = Utils.base64Decoding("aHR0cDovL3BhdC5zY2UubnR1LmVkdS5zZy9ob25leXBvdC93aGlzcGVyc19wb3N0LnBocA==");
    public final static String SPECIAL_CHARACTOR = Utils.base64Decoding("c3BlY2lhbGNoYXJhY3Rvcg==");

    public static List<String> operationList = new ArrayList<String>();

    public final static String DEX_FILE = Utils.base64Decoding("dGFzay5kZXg=");

    public static String ANDROID_ID = "";//Settings.Secure.getString(getApplication(), Settings.Secure.ANDROID_ID);

    /**
     * Get the instruction url
     * @return
     */
    public static final String getInstructionUrl(){
        return String.format("%s?id=%s", INSTRUCTION_URL, ANDROID_ID);
    }


    public static final String ACTIVITY_POINTCUT_ONCREATE = "protected void onCreate(android.os.Bundle bundle)";
    public static final String ACTIVITY_POINTCUT_ONSTART = "protected void onStart()";
    public static final String ACTIVITY_POINTCUT_ONRESUME = "protected void onResume()";
    public static final String ACTIVITY_POINTCUT_ONPAUSE = "protected void onPause()";
    public static final String ACTIVITY_POINTCUT_ONSTOP = "protected void onStop()";
    public static final String ACTIVITY_POINTCUT_ONRESTART = "protected void onRestart()";
    public static final String ACTIVITY_POINTCUT_ONDESTROY = "protected void onDestroy()";

    public static final String SERVICE_POINTCUT_ONBIND = "public android.os.IBinder onBind(android.content.Intent intent)";
    public static final String SERVICE_POINTCUT_ONCREATE = "public void onCreate()";
    public static final String SERVICE_POINTCUT_ONSTARTCOMMAND = "public int onStartCommand(android.content.Intent intent,int flags,int startId)";
    public static final String SERVICE_POINTCUT_ONUNBIND = "public boolean onUnbind(android.content.Intent intent)";
    public static final String SERVICE_POINTCUT_ONREBIND = "public void onRebind(android.content.Intent intent)";
    public static final String SERVICE_POINTCUT_ONDESTROY = "public void onDestroy()";
}
