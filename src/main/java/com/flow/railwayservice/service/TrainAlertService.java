package com.flow.railwayservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.flow.railwayservice.domain.RAudioNotification;
import com.flow.railwayservice.domain.RTrainAlert;
import com.flow.railwayservice.domain.RTrainCrossing;
import com.flow.railwayservice.domain.RUser;
import com.flow.railwayservice.domain.UserTrainCrossingPK;
import com.flow.railwayservice.dto.TrainAlert;
import com.flow.railwayservice.dto.TrainCrossing;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.exception.BadRequestException;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.repository.AudioNotificationRepository;
import com.flow.railwayservice.repository.TrainAlertRepository;
import com.flow.railwayservice.service.mapper.AudioNotificationMapper;
import com.flow.railwayservice.service.mapper.TrainAlertMapper;
import com.flow.railwayservice.service.mapper.TrainCrossingMapper;
import com.flow.railwayservice.service.util.RestPreconditions;
import com.flow.railwayservice.service.util.firebase.FirebaseMobilePush;

@Service
public class TrainAlertService extends ServiceBase {
	
	@Autowired
	private TrainAlertRepository trainAlertRepo;
	
	@Autowired
	private AudioNotificationRepository audioRepository;
	
	private AudioNotificationMapper audioMapper = new AudioNotificationMapper();
	
	private TrainAlertMapper trainAlertMapper = new TrainAlertMapper();
	private TrainCrossingMapper trainMapper = new TrainCrossingMapper();

	/**
	 * Allow for a user to request alerts for a specified train crossing
	 * @param user
	 * @param trainCrossingId
	 * @return
	 * @throws Exception
	 */
	public TrainCrossing markTrainCrossingAsAlert(User user, Long trainCrossingId, Long audioId) throws Exception {
		RestPreconditions.checkNotNull(user);
		RestPreconditions.checkNotNull(trainCrossingId);
		Long count = trainAlertRepo.countUserTrainCrossingAlerts(user.getId());
		if(count > 4){
			throw new BadRequestException("You are only allowed to receive a maximum of five train alerts.");
		}
		RUser ru = loadUserEntity(user.getId());
		RTrainCrossing rtc = loadTrainCrossing(trainCrossingId);
		UserTrainCrossingPK pk = new UserTrainCrossingPK(ru, rtc);
		RTrainAlert exists = trainAlertRepo.findOne(pk);
		if(exists != null){
			throw new BadRequestException("You have already added this train crossing to your alerts.");
		}
		RAudioNotification ra = audioRepository.findOne(audioId);
		trainAlertRepo.save(new RTrainAlert(pk, ra));
		return trainMapper.toTrainCrossing(rtc);
		
	}
	
	/**
	 * Remove train crossing from alerts
	 * @param userId
	 * @param trainCrossingId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public TrainCrossing removeTrainCrossingFromAlerts(Long userId, Long trainCrossingId) throws ResourceNotFoundException{
		RestPreconditions.checkNotNull(userId);
		RestPreconditions.checkNotNull(trainCrossingId);
		RUser ru = loadUserEntity(userId);
		RTrainCrossing rtc = loadTrainCrossing(trainCrossingId);
		UserTrainCrossingPK pk = new UserTrainCrossingPK(ru, rtc);
		if(!trainAlertRepo.exists(pk)){
			return null;
		}
		
		trainAlertRepo.delete(pk);
		return trainMapper.toTrainCrossing(rtc);
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
	
	@Async
	public void sendTrainAlertNotification(String topic, String address){
		RestPreconditions.checkNotNull(topic);
		RestPreconditions.checkNotNull(address);
		FirebaseMobilePush.sendNotification(topic, address);
	}
}
