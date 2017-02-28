package com.flow.railwayservice.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * Train crossings
 * @author Dayna
 *
 */
@Entity
@Table(name="train_crossing", indexes={
	@Index(name="train_crossing_topic_idx", columnList="notification_topic"),
	@Index(name="train_crossing_active_idx", columnList="flagged_active, time_flagged_active")
})
public class RTrainCrossing implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8997683857828805276L;

	@Id
    @GeneratedValue
	private Long id;
	
	@Column(name="railway")
	private String railway;
	
	@Embedded
	private RLocation location;
	
	/**
	 * The topic name that users will subscribe to to
	 * receive notifications about this particular train crossing.
	 */
	@Column(name="notification_topic")
	private String notificationTopic;
	
	/**
	 * Flag the train crossing as active if notification alert has been 
	 * sent to users
	 */
	@Column(name="flagged_active")
	private Boolean isFlaggedActive = false;
	
	/**
	 * Store the time in which the train crossing had been marked active
	 */
	@Column(name="time_flagged_active")
	private ZonedDateTime timeFlaggedActive;
	
	public RTrainCrossing(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRailway() {
		return railway;
	}

	public void setRailway(String railway) {
		this.railway = railway;
	}

	public RLocation getLocation() {
		return location;
	}

	public void setLocation(RLocation location) {
		this.location = location;
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
	
	public ZonedDateTime getTimeFlaggedActive(){
		return timeFlaggedActive;
	}
	
	public void setTimeFlaggedActive(ZonedDateTime timeFlaggedActive){
		this.timeFlaggedActive = timeFlaggedActive;
	}
	
}
