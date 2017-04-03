package edu.ntu.android.androguard.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiverDynTester extends BroadcastReceiver {
    public BroadcastReceiverDynTester() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Constants.BROADCAST_MESSAGE)){
            Log.i("DynReceiverTest", "I've received the broadcast message");
        }else{
            Log.i("DynReceiverTest", "No, the action is " + intent.getAction());
        }
    }
}
