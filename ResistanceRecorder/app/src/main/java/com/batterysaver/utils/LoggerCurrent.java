package com.batterysaver.utils;

public class LoggerCurrent extends LoggerBase {
	
	public final String tagCurrent = "current";
	
	static LoggerCurrent instance;
	public static LoggerCurrent getInstance(){
		if(instance == null){
			instance = new LoggerCurrent();
		}
		return instance;
	}
	
	@Override
	protected String getLogFileName() {
		return "current.log";
	}
	@Override
	public String loggingItem(){
		Long current = CurrentReaderFactory.getValue();
		return current+"";
	}
	@Override
	public void triggerLog() {
		Long current = CurrentReaderFactory.getValue();
		log(tagCurrent, current + "");
	}
}
