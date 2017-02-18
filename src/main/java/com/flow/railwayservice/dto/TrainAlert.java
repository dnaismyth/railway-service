package com.flow.railwayservice.dto;

import java.io.Serializable;

public class TrainAlert implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AudioNotification audioNotification;
	private TrainCrossing trainCrossing;
	
	public TrainAlert(){}

	public AudioNotification getAudioNotification() {
		return audioNotification;
	}

	public void setAudioNotification(AudioNotification audioNotification) {
		this.audioNotification = audioNotification;
	}

	public TrainCrossing getTrainCrossing() {
		return trainCrossing;
	}

	public void setTrainCrossing(TrainCrossing trainCrossing) {
		this.trainCrossing = trainCrossing;
	}
	
}
