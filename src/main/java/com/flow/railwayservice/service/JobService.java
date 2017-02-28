package com.flow.railwayservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.flow.railwayservice.domain.RJob;
import com.flow.railwayservice.dto.JobType;
import com.flow.railwayservice.repository.JobRepository;

@Service
public class JobService {
    private final Logger log = LoggerFactory.getLogger(JobService.class);

	@Autowired
	private JobRepository jobRepo;
	
	@Autowired
	private TrainCrossingService trainCrossingService;
	
	private static final int MAX_ACTIVE_TIME = 1200;
	
	/**
	 * Job to update active train crossings
	 * To run every 60000ms
	 */
	@Scheduled(fixedRate = 60000)
	public void monitorActiveTrainCrossings(){
		RJob job = jobRepo.findJobByType(JobType.MONITOR_ACTIVE_TRAIN_CROSSINGS);
		if(job != null && job.isEnabled()){
			log.info("Updating stale train crossings...");
			trainCrossingService.findAndUpdateStaleTrainCrossings(MAX_ACTIVE_TIME);
		}		
	}
	
}
