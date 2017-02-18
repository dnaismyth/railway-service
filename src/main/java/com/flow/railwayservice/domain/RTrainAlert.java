package com.flow.railwayservice.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="train_alert")
public class RTrainAlert implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private UserTrainCrossingPK userTrainCrossingPK;
	
	@OneToOne
	private RAudioNotification audioNotification;

	public RTrainAlert(){}
	
	public RTrainAlert(UserTrainCrossingPK userTrainCrossingPK, RAudioNotification audioNotification){
		this.userTrainCrossingPK = userTrainCrossingPK;
		this.audioNotification = audioNotification;
	}
	
	public UserTrainCrossingPK getUserTrainCrossingPK() {
		return userTrainCrossingPK;
	}

	public void setUserTrainCrossingPK(UserTrainCrossingPK userTrainCrossingPK) {
		this.userTrainCrossingPK = userTrainCrossingPK;
	}

	public RAudioNotification getAudio() {
		return audioNotification;
	}

	public void setAudio(RAudioNotification audioNotification
			) {
		this.audioNotification = audioNotification;
	}
	
}
