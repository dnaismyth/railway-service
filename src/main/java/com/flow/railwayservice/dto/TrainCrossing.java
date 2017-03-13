package com.flow.railwayservice.dto;

import java.io.Serializable;

public class TrainCrossing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String railway;
	private Location location;
	private Boolean isMarkedForAlerts = false;
	private String notificationTopic;
	private Boolean isFlaggedActive;
	private String lastFlaggedActive;	// String representation of time passed since last time train had been flagged as active
	
	public TrainCrossing(){}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getRailway(){
		return railway;
	}
	
	public void setRailway(String railway){
		this.railway = railway;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public void setLocation(Location location){
		this.location = location;
	}
	
	public Boolean isMarkedForAlerts(){
		return isMarkedForAlerts;
	}
	
	public void setIsMarkedForAlerts(Boolean isMarkedForAlerts){
		this.isMarkedForAlerts = isMarkedForAlerts;
	}
	
	public String getNotificationTopic(){
		return notificationTopic;
	}
	
	public void setNotificationTopic(String notificationTopic){
		this.notificationTopic = notificationTopic;
	}
	
	public Boolean isFlaggedActive(){
		return isFlaggedActive;
	}
	
	public void setIsFlaggedActive(Boolean isFlaggedActive){
		this.isFlaggedActive = isFlaggedActive;
	}
	
	public String getLastFlaggedActive(){
		return lastFlaggedActive;
	}
	
	public void setLastFlaggedActive(String lastFlaggedActive){
		this.lastFlaggedActive = lastFlaggedActive;
	}
}
