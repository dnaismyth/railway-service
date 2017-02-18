package com.flow.railwayservice.dto;

import java.io.Serializable;

public class TrainCrossing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Long id;
	public String railway;
	public Location location;
	
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
}