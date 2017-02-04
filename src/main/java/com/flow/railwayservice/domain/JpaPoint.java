package com.flow.railwayservice.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.vividsolutions.jts.geom.Point;

/**
 * Point object for location
 * @author Dayna
 *
 */
@Embeddable
public class JpaPoint {

	@Column(name="x_coordinate")
	double x;
	
	@Column(name="y_coordinate")
	double y;
		
	public JpaPoint(){}
	
	public JpaPoint(Point point){
		this.x = point.getCoordinate().x;
		this.y = point.getCoordinate().y;
	}
}
