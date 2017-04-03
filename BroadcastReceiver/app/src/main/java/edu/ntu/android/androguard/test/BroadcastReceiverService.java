package edu.ntu.android.androguard.test;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BroadcastReceiverService extends Service {
    public BroadcastReceiverService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        Intent broMessage = new Intent();
        broMessage.setAction(Constants.BROADCAST_MESSAGE);
        sendBroadcast(broMessage);
        Log.i("service", "Send a broadcast message from service");
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
