package com.flow.railwayservice.service.mapper;

import com.flow.railwayservice.domain.RTrainCrossing;
import com.flow.railwayservice.service.dto.TrainCrossing;

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
		}
		return rtc;
	}
}
