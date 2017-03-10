package com.flow.railwayservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flow.railwayservice.domain.RTrainCrossing;

public interface TrainCrossingRepository extends JpaRepository<RTrainCrossing, Long> {
	
	@Query("SELECT rt FROM RTrainCrossing rt WHERE rt.location.province = 'BC'")
	public Page<RTrainCrossing> findAllTrainCrossingsInBC(Pageable pageable);
	
	@Query("SELECT rt FROM RTrainCrossing rt WHERE rt.isFlaggedActive = true AND rt.timeFlaggedActive IS NOT NULL")
	public List<RTrainCrossing> findActiveTrainCrossings();
	
	@Query("SELECT rt FROM RTrainCrossing rt WHERE rt.isFormatted != true OR rt.isFormatted IS NULL")
	public Page<RTrainCrossing> findTrainCrossingsToFormat(Pageable pageable);
}
