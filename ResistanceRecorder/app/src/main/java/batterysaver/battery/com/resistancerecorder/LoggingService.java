package batterysaver.battery.com.resistancerecorder;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.batterysaver.utils.LoggerBase;
import com.batterysaver.utils.LoggerItems;
import com.batterysaver.utils.LoggerResistance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LoggingService extends Service {

    private List<LoggedFile> fileList = new ArrayList<LoggedFile>();
    public static final String START_LOGGING = "com.batterysaver.logging.start";
    public static final String STOP_LOGGING = "com.batterysaver.logging.stop";

    private Runnable runnable = new Runnable(){
        @Override
        public void run(){
            LoggerItems.getInstance().triggerLog();
        }
    };

    ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
    ScheduledFuture future;
    //private AlarmManager am = null;
    //private PendingIntent pi = null;

    private NotificationManager mNotificationManager;

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(START_LOGGING)){
                /*if (am == null) am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                if (pi == null) pi = PendingIntent.getService(getApplicationContext(), 0, new Intent(getApplicationContext(), LoggingIntentService.class), 0);
                am.setRepeating(AlarmManager.ELAPSED_REALTIME, 0, Constants.getFrequency(), pi);*/
                future = exec.scheduleAtFixedRate(runnable, 0, Constants.getFrequency(), TimeUnit.MILLISECONDS);
                mNotificationManager.notify(Constants.LOGGING_SERVICE_ID, makeNotification());
            }else if (intent.getAction().equalsIgnoreCase(STOP_LOGGING)){
                /*if (am != null && pi != null){
                    am.cancel(pi);
                }*/
                future.cancel(true);
                mNotificationManager.notify(Constants.LOGGING_SERVICE_ID, makeNotification());

            }
        }
    };

    public LoggingService() {
    }

    @Override
    public void onCreate(){

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        IntentFilter filter = new IntentFilter();
        filter.addAction(LoggingService.START_LOGGING);
        filter.addAction(LoggingService.STOP_LOGGING);
        registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public void onDestroy(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(LoggingService.START_LOGGING);
        filter.addAction(LoggingService.STOP_LOGGING);
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startForeground(Constants.LOGGING_SERVICE_ID, makeNotification());
        return Service.START_STICKY;
    }

    public Notification makeNotification(){
        String content = null;

        if (!Constants.isRunning())     content = "the logging is not started yet.";
        else{
            content = String.format("the service is logging %d files", Constants.getSelectedFileCount());
        }

        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent notificationIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this).setContentTitle("Logging Service")
                .setContentText(content)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentIntent(notificationIntent).addAction(R.drawable.notification_template_icon_bg, "Logging service is running", notificationIntent).build();
        return notification;
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
            LoggerItems.getInstance().triggerLog();
        }
    }
}
