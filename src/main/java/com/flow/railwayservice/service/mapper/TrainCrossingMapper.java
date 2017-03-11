package com.flow.railwayservice.service.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.flow.railwayservice.domain.RTrainCrossing;
import com.flow.railwayservice.dto.TrainCrossing;

/**
 * Train crossing mapping helper
 * @author Dayna
 *
 */
public class TrainCrossingMapper {
	
	private LocationMapper locationMapper = new LocationMapper();

	/**
	 * To TrainCrossing object
	 * @param rtc
	 * @return
	 */
	public TrainCrossing toTrainCrossing(RTrainCrossing rtc){
		TrainCrossing tc = null;
		if(rtc != null){
			tc = new TrainCrossing();
			tc.setId(rtc.getId());
			tc.setRailway(rtc.getRailway());
			tc.setLocation(locationMapper.toLocation(rtc.getLocation()));
			tc.setNotificationTopic(rtc.getNotificationTopic());
			tc.setIsFlaggedActive(rtc.isFlaggedActive());
			tc.setLastFlaggedActive(rtc.getTimeFlaggedActive());
		}
		return tc;
	}
	
	/**
	 * To RTrainCrossing entity object
	 * @param tc
	 * @return
	 */
	public RTrainCrossing toRTrainCrossing(TrainCrossing tc){
		RTrainCrossing rtc = null;
		if(tc != null){
			rtc = new RTrainCrossing();
			rtc.setId(tc.getId());
			rtc.setLocation(locationMapper.toRLocation(tc.getLocation()));
			rtc.setRailway(tc.getRailway());
			rtc.setNotificationTopic(tc.getNotificationTopic());
			rtc.setIsFlaggedActive(tc.isFlaggedActive());
		}
		return rtc;
	}
	
	public Page<TrainCrossing> toRTrainCrossing(Page<RTrainCrossing> rt, Pageable pageable){
		List<TrainCrossing> trainCrossings = new ArrayList<TrainCrossing>();
		Iterator<RTrainCrossing> iter = rt.iterator();
		while(iter.hasNext()){
			trainCrossings.add(toTrainCrossing(iter.next()));
		}
		return new PageImpl<TrainCrossing>(trainCrossings, pageable, trainCrossings.size());
	}
}
