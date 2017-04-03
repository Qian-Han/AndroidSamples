package edu.ntu.android.androguard.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver innerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Constants.BROADCAST_MESSAGE)){
                Log.i("InnerReceiver", "I've received the broadcast message");
            }else{
                Log.i("InnerReceiver", "No, the action is " + intent.getAction());
            }
        }
    };


    private BroadcastReceiver secondReceiver = new BroadcastReceiverDynTester();

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

        //Register the inner broadcast receiver
        registerReceiver(innerReceiver, new IntentFilter(Constants.BROADCAST_MESSAGE));
        Log.i("MainActivity", "register the inner receiver");

        registerReceiver(secondReceiver, new IntentFilter(Constants.BROADCAST_MESSAGE));
        Log.i("MainActivity", "register the second receiver");

        Log.i("MainActivity", "start a service");
        Intent intent = new Intent(this, BroadcastReceiverService.class);
        startService(intent);
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

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(innerReceiver);
        unregisterReceiver(secondReceiver);
    }
}
