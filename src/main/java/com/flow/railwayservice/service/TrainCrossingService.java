package com.flow.railwayservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flow.railwayservice.domain.RTrainCrossing;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.repository.TrainCrossingRepository;
import com.flow.railwayservice.service.dto.TrainCrossing;
import com.flow.railwayservice.service.mapper.TrainCrossingMapper;
import com.flow.railwayservice.service.util.RestPreconditions;

@Service
@Transactional
public class TrainCrossingService extends ServiceBase {
	
	@Autowired
	private TrainCrossingRepository trainCrossingRepo;
	
	private TrainCrossingMapper trainCrossingMapper = new TrainCrossingMapper();

	/**
	 * Find train crossing by id
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public TrainCrossing getTrainCrossing(Long trainCrossingId) throws ResourceNotFoundException{
		RestPreconditions.checkNotNull(trainCrossingId);
		RTrainCrossing crossing = loadTrainCrossing(trainCrossingId);
		return trainCrossingMapper.toTrainCrossing(crossing);
	}
	
	/**
	 * Create new train crossing, this will be used for admin purposes.
	 * @param crossing
	 * @return
	 */
	public TrainCrossing createTrainCrossing(TrainCrossing crossing){
		RestPreconditions.checkNotNull(crossing);
		RTrainCrossing rtc = trainCrossingMapper.toRTrainCrossing(crossing);
		RTrainCrossing saved = trainCrossingRepo.save(rtc);
		return trainCrossingMapper.toTrainCrossing(saved);
	}
	
	public void deleteTrainCrossing(Long trainCrossingId) throws ResourceNotFoundException{
		RestPreconditions.checkNotNull(trainCrossingId);
		RTrainCrossing rtc = loadTrainCrossing(trainCrossingId);
		trainCrossingRepo.delete(rtc);
	}
}
