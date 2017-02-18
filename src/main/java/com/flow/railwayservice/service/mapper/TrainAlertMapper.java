package com.flow.railwayservice.service.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.flow.railwayservice.domain.RTrainAlert;
import com.flow.railwayservice.dto.TrainAlert;

/**
 * Helper class to map TrainAlert
 * @author Dayna
 *
 */
public class TrainAlertMapper {

	private TrainCrossingMapper trainCrossingMapper = new TrainCrossingMapper();
	private AudioNotificationMapper audioNotificationMapper = new AudioNotificationMapper();
	
	/**
	 * To TrainAlert DTO
	 * @param rta
	 * @return
	 */
	public TrainAlert toTrainAlert(RTrainAlert rta){
		TrainAlert alert = null;
		if(rta != null){
			alert = new TrainAlert();
			alert.setAudioNotification(audioNotificationMapper.toAudioNotification(rta.getAudio()));
			alert.setTrainCrossing(trainCrossingMapper.toTrainCrossing(rta.getUserTrainCrossingPK().getTrainCrossing()));
		}
		
		return alert;
	}
	
	/**
	 * To DTO Page<TrainAlert>
	 * @param rt
	 * @param pageable
	 * @return
	 */
	public Page<TrainAlert> toTrainAlertPage(Page<RTrainAlert> rt, Pageable pageable){
		List<TrainAlert> trainAlerts = new ArrayList<TrainAlert>();
		Iterator<RTrainAlert> iter = rt.iterator();
		while(iter.hasNext()){
			trainAlerts.add(toTrainAlert(iter.next()));
		}
		return new PageImpl<TrainAlert>(trainAlerts, pageable, trainAlerts.size());
	}
}
