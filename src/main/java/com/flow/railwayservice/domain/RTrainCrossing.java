package com.flow.railwayservice.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Train crossings
 * @author Dayna
 *
 */
@Entity
@Table(name="train_crossing")
public class RTrainCrossing {
	
	@Id
	private Long id;
	
	@Column(name="railway")
	private String railway;
	
	@Embedded
	private RLocation location;
	
	public RTrainCrossing(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRailway() {
		return railway;
	}

	public void setRailway(String railway) {
		this.railway = railway;
	}

	public RLocation getLocation() {
		return location;
	}

	public void setLocation(RLocation location) {
		this.location = location;
	}
	
}
