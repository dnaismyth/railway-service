package com.flow.railwayservice.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.flow.railwayservice.domain.RTrainCrossing;
import com.flow.railwayservice.domain.RUser;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.repository.TrainCrossingRepository;
import com.flow.railwayservice.repository.UserRepository;
import com.flow.railwayservice.service.util.RestPreconditions;

public class ServiceBase {
	
	@Autowired
	private TrainCrossingRepository trainCrossingRepo;
	
	@Autowired
	private UserRepository userRepo;

	public RTrainCrossing loadTrainCrossing(Long id) throws ResourceNotFoundException{
		RTrainCrossing crossing = trainCrossingRepo.findOne(id);
		if(crossing == null){
			throw new ResourceNotFoundException("Cannot find train crossing with id: " + id);
		}
		return crossing;
	}
	
	public RUser loadUserEntity(Long userId) throws ResourceNotFoundException{
		RUser ru = userRepo.getOne(userId);
		if(ru == null){
			throw new ResourceNotFoundException("Cannot find user with id: " + userId);
		}
		return ru;
	}
}
