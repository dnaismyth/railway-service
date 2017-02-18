package com.flow.railwayservice.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.flow.railwayservice.dto.TrainAlert;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.service.TrainCrossingAlertService;
import com.flow.railwayservice.web.rest.vm.PageResponse;

@RequestMapping("/api")
@RestController
public class TrainAlertController extends BaseController {
	
	@Autowired
	private TrainCrossingAlertService trainAlertService;

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
}
