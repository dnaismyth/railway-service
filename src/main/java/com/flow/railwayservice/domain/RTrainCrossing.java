package com.flow.railwayservice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@Index(name="train_crossing_topic_idx", columnList="notification_topic")
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
	
}
