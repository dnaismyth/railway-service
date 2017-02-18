package com.flow.railwayservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flow.railwayservice.domain.RTrainAlert;
import com.flow.railwayservice.domain.RTrainCrossing;
import com.flow.railwayservice.domain.RUser;
import com.flow.railwayservice.domain.UserTrainCrossingPK;
import com.flow.railwayservice.dto.TrainAlert;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.exception.BadRequestException;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.repository.TrainAlertRepository;
import com.flow.railwayservice.service.mapper.TrainAlertMapper;
import com.flow.railwayservice.service.util.RestPreconditions;

@Service
public class TrainAlertService extends ServiceBase {
	
	@Autowired
	private TrainAlertRepository trainAlertRepo;
	
	private TrainAlertMapper trainAlertMapper = new TrainAlertMapper();

	/**
	 * Allow for a user to request alerts for a specified train crossing
	 * @param user
	 * @param trainCrossingId
	 * @return
	 * @throws Exception
	 */
	public Long markTrainCrossingAsAlert(User user, Long trainCrossingId) throws Exception {
		RestPreconditions.checkNotNull(user);
		RestPreconditions.checkNotNull(trainCrossingId);
		RUser ru = loadUserEntity(user.getId());
		RTrainCrossing rtc = loadTrainCrossing(trainCrossingId);
		UserTrainCrossingPK pk = new UserTrainCrossingPK(ru, rtc);
		RTrainAlert exists = trainAlertRepo.findOne(pk);
		if(exists != null){
			throw new BadRequestException("You have already added this train crossing to your alerts.");
		}
		
		trainAlertRepo.save(new RTrainAlert(pk, null));
		return trainCrossingId;
		
	}
	
	/**
	 * Remove train crossing from alerts
	 * @param userId
	 * @param trainCrossingId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public boolean removeTrainCrossingFromAlerts(Long userId, Long trainCrossingId) throws ResourceNotFoundException{
		RestPreconditions.checkNotNull(userId);
		RestPreconditions.checkNotNull(trainCrossingId);
		RUser ru = loadUserEntity(userId);
		RTrainCrossing rtc = loadTrainCrossing(trainCrossingId);
		UserTrainCrossingPK pk = new UserTrainCrossingPK(ru, rtc);
		if(!trainAlertRepo.exists(pk)){
			return false;
		}
		
		trainAlertRepo.delete(pk);
		return true;
	}
	
	/**
	 * Find all train crossings in which a user has marked to receive
	 * alerts from.
	 * @param userId
	 * @param pageable
	 * @return
	 */
	public Page<TrainAlert> findUserTrainCrossingAlertPreferences(Long userId, Pageable pageable){
		RestPreconditions.checkNotNull(userId);
		RestPreconditions.checkNotNull(pageable);
		Page<RTrainAlert> rta = trainAlertRepo.findUserTrainCrossingAlertPreferences(userId, pageable);
		return trainAlertMapper.toTrainAlertPage(rta, pageable);
	}
}
