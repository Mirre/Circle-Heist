package com.mirre.ball.utils;

import com.badlogic.gdx.Gdx;

public class Logger {

	private static Logger logger = null;
	private String lastTag = "";
	private String lastMessage = "";
	
	public void logTag(String tag, String message){
		if(getLastTag().equalsIgnoreCase(tag))
			return;
		Gdx.app.log(tag, message);
		setLastTag(tag);
	
	}
	
	public void logMessage(String tag, String message){
		if(getLastMessage().equalsIgnoreCase(message))
			return;
		Gdx.app.log(tag, message);
		setLastMessage(message);
	}

	public static Logger getInstance() {
		if(logger == null)
			logger = new Logger();
		return logger;
	}

	public String getLastTag() {
		return lastTag;
	}

	public void setLastTag(String lastTag) {
		this.lastTag = lastTag;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

}
