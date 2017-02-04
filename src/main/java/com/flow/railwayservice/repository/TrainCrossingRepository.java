package com.flow.railwayservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flow.railwayservice.domain.RTrainCrossing;

public interface TrainCrossingRepository extends JpaRepository<RTrainCrossing, Long> {

}
