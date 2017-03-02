package com.flow.railwayservice.service.util.firebase;

/**
 * Class to represent the data within a FCM notification
 * @author Dayna
 *
 */
public class FCMNotificationData {
	private String title;
	private String body;
	private String url;
	
	public FCMNotificationData(){}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getBody(){
		return body;
	}
	
	public void setBody(String body){
		this.body = body;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
}
