package com.batterysaver.utils;

import java.io.BufferedReader;

public class LoggerEvent extends LoggerBase {

	static LoggerEvent instance;
	private BufferedReader readStream;
	
	public static LoggerEvent getInstance(){
		if(instance == null){
			instance = new LoggerEvent();
		}
		return instance;
	}
	
	@Override
	protected String getLogFileName() {
		return "event.log";
	}

	@Override
	public void startLog() {
	}
	
	@Override
	public void stopLog() {
	}
	
	public void triggerLog(String event){
		log(event);
	}
	
}
