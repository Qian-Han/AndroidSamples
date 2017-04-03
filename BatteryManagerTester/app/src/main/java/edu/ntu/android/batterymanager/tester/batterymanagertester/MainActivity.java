package edu.ntu.android.batterymanager.tester.batterymanagertester;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    public Context mContext;
    private TextView currentNow;
    private TextView currentAverage;
    private TextView propertyCapacity;
    private TextView energyCounter;
    private Button update;

    private TextView temperature;
    private TextView scale;
    private TextView status;
    private TextView voltage;
    private TextView level;
    private TextView present;
    private TextView plugged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

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

        currentNow = (TextView)findViewById(R.id.current_now);
        currentAverage = (TextView) findViewById(R.id.current_average);
        propertyCapacity = (TextView) findViewById(R.id.property_capacity);
        energyCounter = (TextView) findViewById(R.id.energy_counter);

        temperature = (TextView) findViewById(R.id.temperature);
        scale = (TextView) findViewById(R.id.scale);
        status = (TextView) findViewById(R.id.status);
        voltage = (TextView) findViewById(R.id.voltage);
        level = (TextView) findViewById(R.id.level);
        present = (TextView) findViewById(R.id.present);
        plugged = (TextView) findViewById(R.id.plugged);

        update = (Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus();
            }
        });
        updateStatus();

        monitorBatteryState();

    }

    public BroadcastReceiver batteryLevelRcvr;

    private void monitorBatteryState() {
        batteryLevelRcvr = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {

                Toast.makeText(mContext, "Update battery status", Toast.LENGTH_SHORT).show();

                int voltageValue = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
                int temperatureValue = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
                int scaleValue = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int statusValue = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                int levelValue = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                boolean presentValue = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
                int pluggedValue = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

                voltage.setText(voltageValue+"");
                temperature.setText(temperatureValue+"");
                scale.setText(scaleValue+"");
                status.setText(statusValue+"");
                level.setText(levelValue+"");
                present.setText(presentValue+"");

                plugged.setText(pluggedValue+"");
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelRcvr, batteryLevelFilter);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void updateStatus(){
        BatteryManager bm = (BatteryManager)getSystemService(Context.BATTERY_SERVICE);
        currentNow.setText(bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)+"");
        currentAverage.setText(bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE)+"");
        propertyCapacity.setText(bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)+"");
        energyCounter.setText(bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER)+"");
        monitorBatteryState();
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
