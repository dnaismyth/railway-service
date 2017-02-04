package com.flow.railwayservice.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Embeddable location object
 * @author Dayna
 *
 */
@Embeddable
public class RLocation {
	
	@Column(name="region")
	private String region;
	
	@Column(name="province")
	private String province;
	
	@Column(name="address")
	private String address;
	
//	@Column(name="point")
//	private Point point;
	
	@Column(name="municipality")
	private String municipality;
	
	public RLocation(){}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	
	

}
