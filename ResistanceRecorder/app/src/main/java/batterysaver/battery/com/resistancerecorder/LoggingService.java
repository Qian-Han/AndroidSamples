package batterysaver.battery.com.resistancerecorder;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.batterysaver.utils.LoggerResistance;

public class LoggingService extends Service {
    public LoggingService() {
    }

    @Override
    public void onCreate(){
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME, 0, 1000, PendingIntent.getService(this, 0, new Intent(this.getApplicationContext(), LoggingIntentService.class), 0));
        //am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 0, 5 * 1000, PendingIntent.getService(this.getApplicationContext(), 0, new Intent(this.getApplicationContext(), LoggingIntentService.class), 0));

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class LoggingIntentService extends IntentService {
        public LoggingIntentService(){
            super("");
        }
        public LoggingIntentService(String name){
            super(name);
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            //Log.e("LoggingIntentService", "I'm logging something");
            LoggerResistance.getInstance().triggerLog();
        }
    }
}
