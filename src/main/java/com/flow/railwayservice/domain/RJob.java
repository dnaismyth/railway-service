package com.flow.railwayservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.flow.railwayservice.dto.JobType;

/**
 * Entity to store current running jobs
 * @author Dayna
 *
 */
@Entity
@Table(name="job", indexes={
		@Index(name="job_type_enabled_idx", columnList="job_type, enabled")
})
public class RJob {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="job_type")
    @Enumerated(EnumType.STRING)
	private JobType jobType;
	
	@Column(name="enabled")
	private Boolean enabled = false;
	
	public RJob(){}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public JobType getJobType(){
		return jobType;
	}
	
	public void setJobType(JobType jobType){
		this.jobType = jobType;
	}
	
	public Boolean isEnabled(){
		return enabled;
	}
	
	public void setIsEnabled(Boolean enabled){
		this.enabled = enabled;
	}
	
	
}
