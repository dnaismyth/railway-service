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
	
	public JpaPoint(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX(){
		return x;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
}
