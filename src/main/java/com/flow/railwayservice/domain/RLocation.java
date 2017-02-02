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
	
	@Column(name="provice")
	private String provice;
	
	@Column(name="address")
	private String address;
	
	@Column(name="latitude")
	private Float latitude;
	
	@Column(name="longitude")
	private Float longitude;
	
	@Column(name="municipality")
	private String municipality;
	
	public RLocation(){}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	
	

}
