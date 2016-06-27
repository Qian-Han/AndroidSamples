package edu.ntu.android.learning.reflection;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final TextView textImei = (TextView) findViewById(R.id.imeiText);
        final TextView textImsi = (TextView) findViewById(R.id.imsiText);
        final TextView textNumber = (TextView) findViewById(R.id.numberText);

        Button buttonImei = (Button)findViewById(R.id.imei);
        buttonImei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                try{
                    Class<?> c = Class.forName("android.telephony.TelephonyManager");
                    try{
                        Method mtd = c.getDeclaredMethod("getDeviceId");
                        Object obj = mtd.invoke(tm);
                        textImei.setText(obj.toString());
                    }catch(NoSuchMethodException e){
                        e.printStackTrace();
                    }catch(IllegalAccessException e){
                        e.printStackTrace();
                    }catch(InvocationTargetException e){
                        e.printStackTrace();
                    }
                }catch(ClassNotFoundException e){
                    e.printStackTrace();
                }
                //textImei.setText(tm.getDeviceId());
            }
        });
        Button buttonImsi = (Button)findViewById(R.id.imsi);
        buttonImsi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                try{
                    Class<?> c = Class.forName("android.telephony.TelephonyManager");
                    try{
                        Method mtd = c.getDeclaredMethod("getSubscriberId");
                        Object obj = mtd.invoke(tm);
                        textImsi.setText(obj.toString());
                    }catch(NoSuchMethodException e){
                        e.printStackTrace();
                    }catch(IllegalAccessException e){
                        e.printStackTrace();
                    }catch(InvocationTargetException e){
                        e.printStackTrace();
                    }
                }catch(ClassNotFoundException e){
                    e.printStackTrace();
                }
                //textImsi.setText(tm.getSubscriberId());
            }
        });
        Button buttonNumber = (Button)findViewById(R.id.number);
        buttonNumber.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                try{
                    Class<?> c = Class.forName("android.telephony.TelephonyManager");
                    try{
                        Method mtd = c.getDeclaredMethod("getLine1Number");
                        Object obj = mtd.invoke(tm);
                        textNumber.setText(obj.toString());
                    }catch(NoSuchMethodException e){
                        e.printStackTrace();
                    }catch(IllegalAccessException e){
                        e.printStackTrace();
                    }catch(InvocationTargetException e){
                        e.printStackTrace();
                    }
                }catch(ClassNotFoundException e){
                    e.printStackTrace();
                }
                //textNumber.setText(tm.getLine1Number());
            }
        });
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }*/

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
