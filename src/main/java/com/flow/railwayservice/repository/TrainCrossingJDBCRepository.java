package com.flow.railwayservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.flow.railwayservice.dto.Location;
import com.flow.railwayservice.dto.TrainCrossing;

@Repository
public class TrainCrossingJDBCRepository extends BaseJDBCRepository {
	
	private static final String SQL_QUERY_NEARBY_TRAINCROSSINGS = "sql.traincrossing.findNearbyTrainCrossings";
	
	public List<TrainCrossing> findNearbyTrainCrossings(double latitude, double longitude, double radius){
		String query = readQueryFromProperties(SQL_QUERY_NEARBY_TRAINCROSSINGS);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("radius", radius);
		params.put("longitude", longitude);
		params.put("latitude", latitude);
		return jdbcTemplate.query(query, params, new TrainCrossingMapper());
	}
	
	public class TrainCrossingMapper implements RowMapper<TrainCrossing> {
		   public TrainCrossing mapRow(ResultSet rs, int rowNum) throws SQLException {
		      TrainCrossing crossing = new TrainCrossing();
		      crossing.setId((Long)rs.getObject("id"));
		      Location loc = new Location();
		      loc.setAddress(rs.getString("address"));
		      loc.setCity(rs.getString("city"));
		      loc.setLatitude(rs.getDouble("x_coordinate"));
		      loc.setLongitude(rs.getDouble("y_coordinate"));
		      loc.setProvince(rs.getString("province"));
		      loc.setRegion(rs.getString("region"));
		      crossing.setLocation(loc);
		      crossing.setRailway(rs.getString("railway"));
		      return crossing;
		   }
	}

}
