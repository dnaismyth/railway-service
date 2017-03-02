package com.flow.railwayservice.service.util.firebase;

/**
 * Class to represent the FCM Notification body
 * @author Dayna
 *
 */
public class FCMNotification {
	private String title;
	private String body;
	//private String sound;  Might add this in to pass through sound link
	
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
}
