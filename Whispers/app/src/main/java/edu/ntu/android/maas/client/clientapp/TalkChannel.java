package edu.ntu.android.maas.client.clientapp;

import android.os.Handler;

/**
 * Created by pillar on 4/19/2016.
 */
public class TalkChannel {

    public static TalkChannel tc = null;

    private TalkChannel(){
    }
    public synchronized static TalkChannel v(){
        if (tc == null){
            tc = new TalkChannel();
        }
        return tc;
    }

    private Handler handler = null;
    public void setServiceHandler(Handler handler){
        this.handler = handler;
    }

    public Handler getServiceHandler(){
        return this.handler;
    }
}
