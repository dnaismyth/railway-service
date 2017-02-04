package com.flow.railwayservice.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

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
	
	@Embedded
	private JpaPoint point;
	
	@Column(name="city")
	private String city;
	
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public JpaPoint getPoint(){
		return point;
	}
	
	public void setJpaPoint(Point point){
		this.point = new JpaPoint(point);
	}
	
}
