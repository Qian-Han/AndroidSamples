package edu.ntu.android.learning.receiveincomingsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IncomingSmsReceiver extends BroadcastReceiver {
    public IncomingSmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")){
            Intent i = new Intent(context, AlertDialogActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
