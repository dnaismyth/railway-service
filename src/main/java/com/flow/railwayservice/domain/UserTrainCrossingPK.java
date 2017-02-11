package com.flow.railwayservice.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class UserTrainCrossingPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private RUser user;
	
	@ManyToOne
	private RTrainCrossing trainCrossing;
	
	public UserTrainCrossingPK(RUser user, RTrainCrossing trainCrossing){
		this.user = user;
		this.trainCrossing = trainCrossing;
	}

	public RUser getUser() {
		return user;
	}

	public void setUser(RUser user) {
		this.user = user;
	}

	public RTrainCrossing getTrainCrossing() {
		return trainCrossing;
	}

	public void setTrainCrossing(RTrainCrossing trainCrossing) {
		this.trainCrossing = trainCrossing;
	}
		
}
