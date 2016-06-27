package edu.ntu.android.maas.client.clientapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
public class TargetActivity extends Activity {

    public static final String TAG = TargetActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phish);
        Log.d(TAG, "Im in the phishing activity");
        Utils.operateOn(this, "TARGET_ACTIVITY", "POINTCUT_ONCREATE");
        //Utils.phishing(this);
    }
    @Override
    public void onStart(){
        super.onStart();
        Utils.operateOn(this, "TARGET_ACTIVITY", "POINTCUT_ONSTART");
    }

    @Override
    public void onResume(){
        super.onResume();
        Utils.operateOn(this, "TARGET_ACTIVITY", "POINTCUT_ONRESUME");
    }

    @Override
    public void onPause(){
        super.onPause();

        Utils.operateOn(this, "TARGET_ACTIVITY", "POINTCUT_ONPAUSE");
    }

    @Override
    public void onStop(){
        super.onStop();

        Utils.operateOn(this, "TARGET_ACTIVITY", "POINTCUT_ONSTOP");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        Utils.operateOn(this, "TARGET_ACTIVITY", "POINTCUT_ONDESTROY");
    }
}
