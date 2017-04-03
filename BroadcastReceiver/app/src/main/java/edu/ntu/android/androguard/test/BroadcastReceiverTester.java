package edu.ntu.android.androguard.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiverTester extends BroadcastReceiver {
    public BroadcastReceiverTester() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Constants.BROADCAST_MESSAGE)){
            Log.i("BroadcastReceiverTest", "I've received the broadcast message");
        }else{
            Log.i("BroadcastReceiverTest", "No, the action is " + intent.getAction());
        }
    }
}
