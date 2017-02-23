package com.flow.railwayservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Service
@Transactional
public class TrainCrossingService extends ServiceBase {
	
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
		//Page<RTrainCrossing> allTrainCrossings = trainCrossingRepo.findAll(pageable);
		Page<RTrainCrossing> allBCTrainCrossings = trainCrossingRepo.findAllTrainCrossingsInBC(pageable);
		return trainCrossingMapper.toRTrainCrossing(allBCTrainCrossings, pageable);
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
	
}
