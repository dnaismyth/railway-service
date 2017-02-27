package com.flow.railwayservice.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.flow.railwayservice.dto.TrainCrossingReport;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.service.TrainCrossingReportService;

@RestController
@RequestMapping(value="/api")
public class TrainCrossingReportController extends BaseController {
	
	@Autowired
	private TrainCrossingReportService reportService;

	/**
	 * Allow for a user to report a train crossing activity
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/traincrossings/{id}/reports", method = RequestMethod.POST)
	@ResponseBody
	public TrainCrossingReport makeTrainCrossingReport(@PathVariable("id") Long id) throws ResourceNotFoundException{
		User user = getCurrentUser();
		return reportService.makeTrainCrossingReport(user.getId(), id);
	}
}
