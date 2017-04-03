package com.batterysaver.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import batterysaver.battery.com.resistancerecorder.Constants;
import batterysaver.battery.com.resistancerecorder.LoggedFile;

public class LoggerItems extends LoggerBase {

    static LoggerItems instance;
    private BufferedReader readStream;

    public static LoggerItems getInstance(){
        if(instance == null){
            instance = new LoggerItems();
        }
        return instance;
    }

    @Override
    protected String getLogFileName() {
        return "default.log";
    }

    @Override
    public String loggingItem(){
        StringBuilder sb = new StringBuilder();
        for (LoggedFile lf : Constants.fileList){
            if (lf.isChecked()){
                sb.append(Utils.ReadSysfile(lf.getFilePath()) + " ");
            }
        }
        return sb.toString();
    }

    @Override
    public void triggerLog() {
        log(loggingItem());
    }
}
