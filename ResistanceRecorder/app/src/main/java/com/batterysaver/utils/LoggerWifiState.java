package com.batterysaver.utils;

import android.content.Context;
import android.net.wifi.WifiManager;

public class LoggerWifiState extends LoggerBase {
	static LoggerWifiState instance;
	private WifiManager wifiManager = null;
	
	private LoggerWifiState(Context context){
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	}
	
	public static LoggerWifiState getInstance(Context context){
		if(instance == null){
			instance = new LoggerWifiState(context);
		}
		return instance;
	}
	
	@Override
	protected String getLogFileName() {
		return "wifistate.log";
	}
	
	@Override
	public String loggingItem(){
		int wifiState = wifiManager.getWifiState();
		return wifiState+"";
		/*if (wifiState == WifiManager.WIFI_STATE_DISABLED)
			return "WIFI_STATE_DISABLED";
		else if (wifiState == WifiManager.WIFI_STATE_DISABLING)
			return "WIFI_STATE_DISABLING";
		else if (wifiState == WifiManager.WIFI_STATE_ENABLED)
			return "WIFI_STATE_ENABLED";
		else if (wifiState == WifiManager.WIFI_STATE_ENABLING)
			return "WIFI_STATE_ENABLING";
		else if (wifiState == WifiManager.WIFI_STATE_UNKNOWN)
			return "WIFI_STATE_UNKNOWN";
		return StringUtil.EMPTY;*/
	}
	
	@Override
	public void triggerLog() {
		String voltage = loggingItem();			
		log(voltage);
	}
}
