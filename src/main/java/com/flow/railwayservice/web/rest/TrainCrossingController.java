package com.flow.railwayservice.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.flow.railwayservice.dto.TrainCrossing;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.service.TrainCrossingService;
import com.flow.railwayservice.web.rest.vm.CollectionResponse;
import com.flow.railwayservice.web.rest.vm.PageResponse;
import com.flow.railwayservice.web.rest.vm.RestResponse;

/**
 * TrainCrossing controller
 * Response will provide TrainCrossing objects
 * @author Dayna
 *
 */
@RestController
@RequestMapping("/api")
public class TrainCrossingController extends BaseController {
	
	@Autowired
	private TrainCrossingService trainCrossingService;
	
	/**
	 * Find one train crossing by id
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/traincrossings/{id}", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<TrainCrossing> getTrainCrossingById(@PathVariable("id") Long id) throws ResourceNotFoundException{
		TrainCrossing tc = trainCrossingService.getTrainCrossing(id);
		return new RestResponse<TrainCrossing>(tc);
	}
	
	/**
	 * Return all train crossings
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/traincrossings", method = RequestMethod.GET)
	@ResponseBody
	public PageResponse<TrainCrossing> getAllTrainCrossings(@RequestParam(PAGE_PARAM) int page, @RequestParam(SIZE_PARAM) int size){
		Page<TrainCrossing> allCrossings = trainCrossingService.getAllTrainCrossings(new PageRequest(page, size));
		return new PageResponse<TrainCrossing>(allCrossings);
	}	
	
	/**
	 * Find all train crossings nearby
	 * @param radius
	 * @return
	 */
	@RequestMapping(value="/nearby/traincrossings", method = RequestMethod.GET)
	@ResponseBody
	public CollectionResponse<TrainCrossing> getNearbyTrainCrossings(@RequestParam(required=false, value = RADIUS_PARAM) Double radius,
			@RequestParam(required=true, value = LAT_PARAM) Double latitude,
			@RequestParam(required = true, value = LON_PARAM) Double longitude){
		User user = getCurrentUser();
		if(radius == null){
			radius = DEFAULT_RADIUS; 	// set a default radius (100)
		}
		
		List<TrainCrossing> nearby = trainCrossingService.getTrainCrossingsNearby(user, latitude, longitude, radius);
		return new CollectionResponse<TrainCrossing>(nearby);		
	}
}
