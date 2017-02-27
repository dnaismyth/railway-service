package com.flow.railwayservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flow.railwayservice.domain.RTrainCrossingReport;

public interface TrainCrossingReportRepository extends JpaRepository<RTrainCrossingReport, Long>{

	@Query("SELECT rtcr FROM RTrainCrossingReport rtcr WHERE rtcr.user.id = ?1 AND rtcr.trainCrossing.id = ?2 ORDER BY rtcr.reportedDate DESC")
	public RTrainCrossingReport findReportByUserAndTrainCrossingId(Long userId, Long trainCrossingId);
}
