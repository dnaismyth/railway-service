package com.flow.railwayservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flow.railwayservice.domain.RTrainCrossing;

public interface TrainCrossingRepository extends JpaRepository<RTrainCrossing, Long> {
	
	@Query("SELECT rt FROM RTrainCrossing rt WHERE rt.location.province = 'BC'")
	Page<RTrainCrossing> findAllTrainCrossingsInBC(Pageable pageable);
}
