package com.flow.railwayservice.service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flow.railwayservice.domain.RTrainCrossing;
import com.flow.railwayservice.domain.RTrainCrossingReport;
import com.flow.railwayservice.domain.RUser;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.repository.TrainCrossingReportRepository;
import com.flow.railwayservice.service.util.RestPreconditions;
import com.flow.railwayservice.service.util.TimeUtil;

/**
 * Report an active train crossing
 * 
 * @author Dayna
 *
 */
@Service
@Transactional
public class TrainCrossingReportService extends ServiceBase {

	@Autowired
	private TrainCrossingReportRepository reportRepo;

	private static final int MAX_MINUTE_DELAY = 3600; // only allow users to
														// report the same train
														// crossing once every
														// hour.

	/**
	 * Allow for a user to make a train crossing report.
	 * 
	 * @param userId
	 * @param trainCrossingId
	 * @throws ResourceNotFoundException
	 */
	public boolean makeTrainCrossingReport(Long userId, Long trainCrossingId) throws ResourceNotFoundException {
		RestPreconditions.checkNotNull(userId);
		RestPreconditions.checkNotNull(trainCrossingId);
		RUser reporter = loadUserEntity(userId);
		RTrainCrossing crossing = loadTrainCrossing(trainCrossingId);
		
		RTrainCrossingReport lastReport = reportRepo.findReportByUserAndTrainCrossingId(userId, trainCrossingId);
		if (lastReport != null) {
			long timeDifference = TimeUtil.getZonedDateTimeDifference(TimeUtil.getCurrentTime(), lastReport.getReportedDate(),
					ChronoUnit.SECONDS) ;
			// If there exists a previous report, we check to see that the new report is made at least an hour after the previous
			if (timeDifference > MAX_MINUTE_DELAY) {
				RTrainCrossingReport report = new RTrainCrossingReport(crossing, reporter);
				reportRepo.save(report);
				return true;
			}
		} else {
			RTrainCrossingReport report = new RTrainCrossingReport(crossing, reporter);
			reportRepo.save(report);
			return true;
		}

		return false;

	}
}
