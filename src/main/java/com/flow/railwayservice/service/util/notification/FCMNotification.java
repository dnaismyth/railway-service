package com.flow.railwayservice.service.util.notification;

/**
 * Class to represent the FCM Notification body
 * @author Dayna
 *
 */
public class FCMNotification {
	private String title;
	private String body;
	private String sound;
	
	public FCMNotification(){}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}
	
	
}
