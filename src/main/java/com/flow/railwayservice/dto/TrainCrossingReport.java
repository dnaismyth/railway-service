package com.flow.railwayservice.dto;

/**
 * TrainCrossingReport response
 * @author Dayna
 *
 */
public class TrainCrossingReport {
	
	private TrainCrossing trainCrossing;
	private OperationType operationType;
	
	public TrainCrossingReport(){}
	
	public TrainCrossingReport(TrainCrossing trainCrossing, OperationType operationType){
		this.trainCrossing = trainCrossing;
		this.operationType = operationType;
	}

	public TrainCrossing getTrainCrossing() {
		return trainCrossing;
	}

	public void setTrainCrossing(TrainCrossing trainCrossing) {
		this.trainCrossing = trainCrossing;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	
	
}
