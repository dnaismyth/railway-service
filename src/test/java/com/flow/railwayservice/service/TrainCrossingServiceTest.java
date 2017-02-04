package com.flow.railwayservice.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.flow.railwayservice.dto.Location;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.service.dto.TrainCrossing;


public class TrainCrossingServiceTest extends BaseServiceTest {
	
	@Autowired 
	private TrainCrossingService trainCrossingService;
	
	private List<Long> trainCrossingIds;
	
	@Before
	public void setUp(){
		trainCrossingIds = new ArrayList<Long>();
	}
	
	@After
	public void tearDown() throws ResourceNotFoundException{
		for(Long id : trainCrossingIds){
			//trainCrossingService.deleteTrainCrossing(id);
		}
	}
	
	// Test that a new train crossing can be created
	@Test
	public void testCreateTrainCrossing(){
		Location location = new Location();
		location.setAddress("Testing");
		location.setLatitude(149.00);
		location.setLongitude(49.00);
		TrainCrossing tc = new TrainCrossing();
		tc.setRailway("CN");
		tc.setLocation(location);
		TrainCrossing created = trainCrossingService.createTrainCrossing(tc);
		trainCrossingIds.add(created.getId());
		Assert.assertEquals("CN", created.getRailway());	
	}
}
