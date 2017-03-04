package com.flow.railwayservice.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flow.railwayservice.domain.RTrainCrossing;
import com.flow.railwayservice.dto.TrainCrossing;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.repository.TrainAlertRepository;
import com.flow.railwayservice.repository.TrainCrossingJDBCRepository;
import com.flow.railwayservice.repository.TrainCrossingRepository;
import com.flow.railwayservice.service.mapper.TrainCrossingMapper;
import com.flow.railwayservice.service.util.RestPreconditions;
import com.flow.railwayservice.service.util.TimeUtil;
import com.flow.railwayservice.service.util.firebase.FirebaseDatabase;

@Service
@Transactional
public class TrainCrossingService extends ServiceBase {
	private static final Logger log = LoggerFactory.getLogger(TrainCrossingService.class);

	@Autowired
	private TrainCrossingRepository trainCrossingRepo;
	
	@Autowired
	private TrainCrossingJDBCRepository trainCrossingJDBCRepo;
	
	@Autowired
	private TrainAlertRepository trainAlertRepo;
	
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
	 * Get nearby train crossings
	 * @param user
	 * @param radius
	 * @return
	 */
	public List<TrainCrossing> getTrainCrossingsNearby(User user, double latitude, double longitude, int radius){
		RestPreconditions.checkNotNull(radius);
		RestPreconditions.checkNotNull(latitude);
		RestPreconditions.checkNotNull(longitude);
		RestPreconditions.checkNotNull(user);
		List<TrainCrossing> nearbyTrainCrossings = trainCrossingJDBCRepo.findNearbyTrainCrossings(latitude, longitude, radius);
		Map<Long, TrainCrossing> crossingIdMap = buildTrainCrossingIdMap(nearbyTrainCrossings);
		List<Long> trainIds = trainAlertRepo.flagUserTrainAlertsByTrainCrossingIds(user.getId(), crossingIdMap.keySet());
		markUserTrainCrossingAlerts(trainIds, crossingIdMap);
		return nearbyTrainCrossings;
	}
	
	/**
	 * Helper method to mark query results as user selected alerts
	 * @param trainAlertIds
	 * @param crossingIdMap
	 */
	private void markUserTrainCrossingAlerts(List<Long> trainAlertIds, Map<Long, TrainCrossing > crossingIdMap){
		if(trainAlertIds.size() > 0){
			for(Long id : trainAlertIds){
				TrainCrossing tc = crossingIdMap.get(id);
				if(tc != null){
					tc.setIsMarkedForAlerts(true);
				}
			}
		}
	}
	
	/**
	 * Create a map for train crossings and their corresponding ids.
	 * @param trainCrossings
	 * @return
	 */
	private Map<Long, TrainCrossing> buildTrainCrossingIdMap(List<TrainCrossing> trainCrossings){
		Map<Long, TrainCrossing> trainIdMap = new HashMap<Long, TrainCrossing>();
		if(trainCrossings.size() > 0){
			for(TrainCrossing tc : trainCrossings){
				trainIdMap.put(tc.getId(), tc);
			}
		}
		return trainIdMap;
	}
	
	/**
	 * Get all train crossings
	 * @param pageable
	 * @return
	 */
	public Page<TrainCrossing> getAllTrainCrossings(Pageable pageable){
		RestPreconditions.checkNotNull(pageable);
		Page<RTrainCrossing> allTrainCrossings = trainCrossingRepo.findAll(pageable);
		//Page<RTrainCrossing> allBCTrainCrossings = trainCrossingRepo.findAllTrainCrossingsInBC(pageable);
		return trainCrossingMapper.toRTrainCrossing(allTrainCrossings, pageable);
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
	
	/**
	 * Delete a train crossing
	 * @param trainCrossingId
	 * @throws ResourceNotFoundException
	 */
	public void deleteTrainCrossing(Long trainCrossingId) throws ResourceNotFoundException{
		RestPreconditions.checkNotNull(trainCrossingId);
		RTrainCrossing rtc = loadTrainCrossing(trainCrossingId);
		trainCrossingRepo.delete(rtc);
	}
	
	/**
	 * Update crossings that have been marked active for more than the input maxActiveTime
	 * and set flaggedActive = false
	 */
	public void findAndUpdateStaleTrainCrossings(int maxActiveTimeSeconds){
		List<RTrainCrossing> crossings = trainCrossingRepo.findActiveTrainCrossings();
		List<RTrainCrossing> updated = new ArrayList<RTrainCrossing>();
		for(RTrainCrossing rtc : crossings){
			long timeDifference = TimeUtil.getZonedDateTimeDifference(TimeUtil.getCurrentTime(),
					rtc.getTimeFlaggedActive(), ChronoUnit.SECONDS);
			if(timeDifference >= maxActiveTimeSeconds){
				log.debug("Updating train crossing with id={}", rtc.getId());
				rtc.setIsFlaggedActive(false);
				updated.add(rtc);
				FirebaseDatabase.updateTrainCrossing(rtc.getId(), false, 0); // update real time data, set default values
			}
		}
		
		// Save updated train crossings that were marked as no longer active
		if(updated.size() > 0){
			trainCrossingRepo.save(updated);
		}
	}
}
