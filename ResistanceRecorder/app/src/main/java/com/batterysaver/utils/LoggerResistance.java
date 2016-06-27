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

public class LoggerResistance extends LoggerBase {

	static LoggerResistance instance;
	private BufferedReader readStream;
	// Different model may have the file in different locations
	public static String RESISTANCE_PATH = "/sys/class/power_supply/bms/resistance";
	public static String RESISTANCE_ID_PATH = "/sys/class/power_supply/bms/resistance_id";
	
	
	public static LoggerResistance getInstance(){
		if(instance == null){
			instance = new LoggerResistance();
		}
		return instance;
	}
	
	@Override
	protected String getLogFileName() {
		return "resistance.log";
	}
	
	@Override
	public String loggingItem(){
		String tmp = Utils.ReadSysfile(RESISTANCE_PATH);
		if (tmp != null)
			return tmp;
		return "";
	}

	@Override
	public void triggerLog() {
		String resistance = Utils.ReadSysfile(RESISTANCE_PATH);
		String resistanceId = Utils.ReadSysfile(RESISTANCE_ID_PATH);
		log(resistance+" "+resistanceId);
	}
}
