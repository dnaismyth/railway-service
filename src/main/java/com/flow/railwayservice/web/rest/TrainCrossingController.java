package com.flow.railwayservice.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.service.TrainCrossingService;
import com.flow.railwayservice.service.dto.TrainCrossing;
import com.flow.railwayservice.web.rest.vm.PageResponse;
import com.flow.railwayservice.web.rest.vm.RestResponse;

@RestController
@RequestMapping("/api/traincrossings")
public class TrainCrossingController extends BaseController {
	
	@Autowired
	private TrainCrossingService trainCrossingService;
	
	/**
	 * Find one train crossing by id
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
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
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageResponse<TrainCrossing> getAllTrainCrossings(@RequestParam(PAGE_PARAM) int page, @RequestParam(SIZE_PARAM) int size){
		Page<TrainCrossing> allCrossings = trainCrossingService.getAllTrainCrossings(new PageRequest(page, size));
		return new PageResponse<TrainCrossing>(allCrossings);
	}	
}
