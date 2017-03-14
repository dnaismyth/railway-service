package com.flow.railwayservice.service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.flow.railwayservice.domain.RTrainCrossing;
import com.flow.railwayservice.domain.RTrainCrossingReport;
import com.flow.railwayservice.domain.RUser;
import com.flow.railwayservice.dto.OperationType;
import com.flow.railwayservice.dto.TrainCrossingReport;
import com.flow.railwayservice.dto.UserRole;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.repository.TrainCrossingReportRepository;
import com.flow.railwayservice.repository.TrainCrossingRepository;
import com.flow.railwayservice.service.mapper.TrainCrossingMapper;
import com.flow.railwayservice.service.util.RestPreconditions;
import com.flow.railwayservice.service.util.TimeUtil;
import com.flow.railwayservice.service.util.firebase.FirebaseDatabase;

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
	
	@Autowired
	private TrainAlertService alertService;
	
	@Autowired
	private TrainCrossingRepository crossingRepo;

	private static final long MAX_TRAIN_CROSSING_DELAY = 1200; // max window of time at a train crossing (20 minutes)
	
	private static final int MAX_MINUTE_DELAY = 0; // only allow one report per hour (for the same train crossing)
	
	private static final int TRIGGER_NOTIFICATION_COUNT = 0;

	private TrainCrossingMapper trainCrossingMapper = new TrainCrossingMapper();

	/**
	 * Allow for a user to make a train crossing report.
	 * 
	 * @param userId
	 * @param trainCrossingId
	 * @throws ResourceNotFoundException
	 */
	public TrainCrossingReport makeTrainCrossingReport(Long userId, Long trainCrossingId)
			throws ResourceNotFoundException {
		RestPreconditions.checkNotNull(userId);
		RestPreconditions.checkNotNull(trainCrossingId);
		RUser reporter = loadUserEntity(userId);
		RTrainCrossing crossing = loadTrainCrossing(trainCrossingId);
		TrainCrossingReport reportDTO = new TrainCrossingReport(null, OperationType.NO_CHANGE);
		boolean isReported = false;
		// Check if the current role is USER.
		if (reporter.getRole().equals(UserRole.USER)) {
			Page<RTrainCrossingReport> reports = reportRepo.findReportByUserAndTrainCrossingId(userId, trainCrossingId, new PageRequest(0,1));
			RTrainCrossingReport lastReport = reports.getNumberOfElements() > 0 ? reports.getContent().get(0) : null;
			if (lastReport != null) {
				long timeDifference = TimeUtil.getZonedDateTimeDifference(TimeUtil.getCurrentTime(),
						lastReport.getReportedDate(), ChronoUnit.SECONDS);
				// If there exists a previous report, we check to see that the
				// new report is made at least an hour after the previous
				if (timeDifference > MAX_MINUTE_DELAY) {
					RTrainCrossingReport report = new RTrainCrossingReport(crossing, reporter);
					reportRepo.save(report);
					isReported = true;
					reportDTO = new TrainCrossingReport(trainCrossingMapper.toTrainCrossing(crossing, false), OperationType.CREATE);
				}
			} else {
				RTrainCrossingReport report = new RTrainCrossingReport(crossing, reporter);
				reportRepo.save(report);
				isReported = true;
				reportDTO = new TrainCrossingReport(trainCrossingMapper.toTrainCrossing(crossing, false), OperationType.CREATE);

			}
		}
		//TODO: Handle the case where the current user is admin - send immediate notification?
		if(isReported){
			Long reportCount = activeTrainReportCount(trainCrossingId);
			if(reportCount > TRIGGER_NOTIFICATION_COUNT){
				// Flag the crossing as active and set the time it had been triggered
				crossing.setIsFlaggedActive(true);
				crossing.setTimeFlaggedActive(ZonedDateTime.now());
				crossingRepo.save(crossing);
				alertService.sendTrainAlertNotification(crossing.getNotificationTopic(), crossing.getLocation().getAddress());
				FirebaseDatabase.updateTrainCrossingStatus(trainCrossingId, true);	// set the status in firebase to active of the current train crossing
			}
		}
		return reportDTO;

	}
	
	/**
	 * Count the amount of reports made for a specific train crossing within
	 * the 20 minute window
	 * @param trainCrossingId
	 * @return
	 */
	public Long activeTrainReportCount(Long trainCrossingId){
		Long reportCount = reportRepo.countTrainReportsWithinTimePeriod(ZonedDateTime.now().minusSeconds(MAX_TRAIN_CROSSING_DELAY));
		return reportCount;
	}
	
}
