package com.flow.railwayservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flow.railwayservice.domain.RTrainAlert;
import com.flow.railwayservice.domain.UserTrainCrossingPK;

public interface TrainAlertRepository extends JpaRepository<RTrainAlert, UserTrainCrossingPK> {

}
