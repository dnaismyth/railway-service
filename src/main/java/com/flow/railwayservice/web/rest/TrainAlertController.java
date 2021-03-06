package com.flow.railwayservice.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.flow.railwayservice.dto.AudioNotification;
import com.flow.railwayservice.dto.OperationType;
import com.flow.railwayservice.dto.TrainAlert;
import com.flow.railwayservice.dto.TrainCrossing;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.service.TrainAlertService;
import com.flow.railwayservice.web.rest.vm.PageResponse;
import com.flow.railwayservice.web.rest.vm.RestResponse;

@RequestMapping("/api")
@RestController
public class TrainAlertController extends BaseController {
	
	@Autowired
	private TrainAlertService trainAlertService;

	/**
	 * Find user train alert notification preferences
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/trainalerts", method = RequestMethod.GET)
	@ResponseBody
	public PageResponse<TrainAlert> findUserTrainAlertPreferences(@RequestParam(required = true, value = PAGE_PARAM) int page, 
			@RequestParam(required = true, value = SIZE_PARAM) int size){
		User user = getCurrentUser();
		Pageable pageable = new PageRequest(page, size);
		Page<TrainAlert> alerts = trainAlertService.findUserTrainCrossingAlertPreferences(user.getId(), pageable);
		return new PageResponse<TrainAlert>(alerts);
	}
	
	/**
	 * Mark train crossing to receive alerts
	 * @param id
	 * @param notification
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/traincrossings/{id}/trainalerts", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<TrainCrossing> addTrainCrossingToAlerts(@PathVariable("id") Long id, 
			@RequestBody final AudioNotification notification) throws Exception{
		User user = getCurrentUser();
		Long audioId = notification.getId();
		TrainCrossing tc = trainAlertService.markTrainCrossingAsAlert(user, id, audioId);
		return new RestResponse<TrainCrossing>(tc, OperationType.CREATE);
	}
	
	/**
	 * Allow for a user to remove a train crossing from alerts
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value = "/traincrossings/{id}/trainalerts", method = RequestMethod.DELETE)
	@ResponseBody
	public RestResponse<TrainCrossing> removeTrainCrossingFromAlerts(@PathVariable("id") Long id) throws ResourceNotFoundException{
		User user = getCurrentUser();
		TrainCrossing tc = trainAlertService.removeTrainCrossingFromAlerts(user.getId(), id);
		if(tc != null){
			return new RestResponse<TrainCrossing>(tc, OperationType.DELETE);
		}
		return new RestResponse<TrainCrossing>(tc, OperationType.NO_CHANGE);
	}
}
