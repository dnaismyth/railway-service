package com.flow.railwayservice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flow.railwayservice.domain.RTrainAlert;
import com.flow.railwayservice.domain.RTrainCrossing;
import com.flow.railwayservice.domain.UserTrainCrossingPK;

public interface TrainAlertRepository extends JpaRepository<RTrainAlert, UserTrainCrossingPK> {

	@Query("SELECT rt FROM RTrainAlert rt WHERE rt.userTrainCrossingPK.user.id = ?1")
	public Page<RTrainAlert> findUserTrainCrossingAlertPreferences(Long userId, Pageable pageable);
	
	@Query("SELECT count(*) FROM RTrainAlert rt WHERE rt.userTrainCrossingPK.user.id = ?1")
	public Long countUserTrainCrossingAlerts(Long userId); 
	
	@Query("SELECT rt.userTrainCrossingPK.trainCrossing.id FROM RTrainAlert rt WHERE rt.userTrainCrossingPK.user.id = ?1"
			+ " AND rt.userTrainCrossingPK.trainCrossing.id IN (?2)")
	public List<Long> flagUserTrainAlertsByTrainCrossingIds(Long userId, Set<Long> trainCrossingIds);
}
