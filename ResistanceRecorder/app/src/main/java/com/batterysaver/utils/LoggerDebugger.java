package com.batterysaver.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import android.os.Process;
import android.util.Log;

public class LoggerDebugger extends LoggerBase{
	int pID;
	private BufferedReader readStream;
	
	public LoggerDebugger() {
		pID = Process.myPid();
	}
	
	static LoggerDebugger instance;
	public static LoggerDebugger getInstance(){
		if(instance == null){
			instance = new LoggerDebugger();
		}
		return instance;
	}
	
	@Override
	protected String getLogFileName() {
		return "debugging.log";
	}
	
	public void triggerLog(String msg) {
		log(msg);
	}
}
