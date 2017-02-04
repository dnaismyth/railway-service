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
			//TODO: Set latitude and longitude
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
			Point point = LocationUtil.createPointFromCoordinates(l.getLongitude(), l.getLatitude());
			rl.setJpaPoint(point);
		}
		return rl;
	}
}
