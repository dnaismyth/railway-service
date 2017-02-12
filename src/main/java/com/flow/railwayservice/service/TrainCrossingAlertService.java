package com.flow.railwayservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flow.railwayservice.domain.RTrainAlert;
import com.flow.railwayservice.domain.RTrainCrossing;
import com.flow.railwayservice.domain.RUser;
import com.flow.railwayservice.domain.UserTrainCrossingPK;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.exception.BadRequestException;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.repository.TrainAlertRepository;
import com.flow.railwayservice.service.util.RestPreconditions;

@Service
public class TrainCrossingAlertService extends ServiceBase {
	
	@Autowired
	private TrainAlertRepository trainAlertRepo;

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
}
