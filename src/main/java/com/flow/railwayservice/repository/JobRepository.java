package com.flow.railwayservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flow.railwayservice.domain.RJob;
import com.flow.railwayservice.dto.JobType;

public interface JobRepository extends JpaRepository<RJob, Long> {
	
	@Query("SELECT rj FROM RJob rj WHERE rj.jobType = ?1")
	public RJob findJobByType(JobType type);
	
}
