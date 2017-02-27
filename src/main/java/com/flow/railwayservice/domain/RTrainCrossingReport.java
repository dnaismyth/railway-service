package com.flow.railwayservice.domain;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;



/**
 * Table to hold data about reports being made for
 * active train crossings
 * @author Dayna
 *
 */
@Entity
@Table(name="train_crossing_report", indexes = {
		@Index(name="crossing_report_date_idx", columnList="reported_date")
})
public class RTrainCrossingReport {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Train crossing that is being reported
	 */
	@ManyToOne
	private RTrainCrossing trainCrossing;
	
	/**
	 * User that is making the report
	 */
	@ManyToOne
	private RUser user;
	
	/**
	 * Time at which the report was made
	 */
    @Column(name = "reported_date", nullable = false)
    @CreatedDate
    private ZonedDateTime reportedDate = ZonedDateTime.now();
    
    public RTrainCrossingReport(){}
    
    public RTrainCrossingReport(RTrainCrossing trainCrossing, RUser user){
    	this.trainCrossing = trainCrossing;
    	this.user = user;
    }

	public RTrainCrossing getTrainCrossing() {
		return trainCrossing;
	}

	public void setTrainCrossing(RTrainCrossing trainCrossing) {
		this.trainCrossing = trainCrossing;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	public RUser getUser() {
		return user;
	}

	public void setUser(RUser user) {
		this.user = user;
	}

	public ZonedDateTime getReportedDate() {
		return reportedDate;
	}

	public void setCreatedDate(ZonedDateTime reportedDate) {
		this.reportedDate = reportedDate;
	}
  
}
