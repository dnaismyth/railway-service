package com.flow.railwayservice.service.mapper;

import com.flow.railwayservice.domain.RLocation;
import com.flow.railwayservice.dto.Location;
import com.flow.railwayservice.service.util.LocationUtil;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

/**
 * Location mapping helper
 * @author Dayna
 *
 */
public class LocationMapper {
	
	/**
	 * Map to a location object
	 * @param rl
	 * @return
	 */
	public Location toLocation(RLocation rl){
		Location l = null;
		if (rl != null){
			l = new Location();
			l.setAddress(rl.getAddress());
			l.setProvince(rl.getProvince());
			l.setCity(rl.getCity());
			l.setRegion(rl.getRegion());
			if(rl.getPoint() != null){
				l.setLatitude(rl.getPoint().getX());
				l.setLongitude(rl.getPoint().getY());
			}
		}
		return l;
	}
	
	/**
	 * Map to an entity location object
	 * @param l
	 * @return
	 */
	public RLocation toRLocation(Location l){
		RLocation rl = null;
		if(l != null){
			rl = new RLocation();
			rl.setAddress(l.getAddress());
			rl.setCity(l.getCity());
			rl.setProvince(rl.getProvince());
			rl.setRegion(l.getRegion());
			rl.setJpaPoint(l.getLatitude(), l.getLongitude());
		}
		return rl;
	}
}
