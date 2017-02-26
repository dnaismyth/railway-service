package com.flow.railwayservice.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.flow.railwayservice.dto.TrainAlert;
import com.flow.railwayservice.dto.TrainCrossing;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.dto.UserRole;
import com.flow.railwayservice.exception.BadRequestException;
import com.flow.railwayservice.exception.ResourceNotFoundException;

public class TrainAlertServiceTest extends BaseServiceTest {
	
	@Autowired 
	private TrainCrossingService trainCrossingService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TrainAlertService trainAlertService;
	
	private List<Long> trainCrossingIds;
	private User user;
	private String userName = "testTrainAlert@flow.com";
	
	@Before
	public void setUp() throws BadRequestException{
		trainCrossingIds = new ArrayList<Long>();
		user = userService.findUserByLogin(userName);
    	if(user == null){
    		user = new User();
    		user.setLogin(userName);
    		user.setPassword("test12");
    		user.setEmail(userName);
    		user.setName("Test user");
    		user.setRole(UserRole.USER);
    		user = userService.createUser(user);
    	}
	}
	
	@After
	public void tearDown() throws ResourceNotFoundException{
		for(Long id : trainCrossingIds){
			trainAlertService.removeTrainCrossingFromAlerts(user.getId(), id);
			trainCrossingService.deleteTrainCrossing(id);
		}
		userService.deleteUser(userName);
	}
	
	@Test
	public void testMarkTrainCrossingAsAlert() throws Exception{
		TrainCrossing tc = new TrainCrossing();
		tc.setRailway("CN");
		TrainCrossing created = trainCrossingService.createTrainCrossing(tc);
		trainCrossingIds.add(created.getId());
		TrainCrossing added = trainAlertService.markTrainCrossingAsAlert(user, created.getId(), null);
		Assert.assertEquals(created.getId(), added.getId());
	}
	
	@Test
	public void testFindUserTrainAlertPreferences() throws Exception{
		TrainCrossing tc = new TrainCrossing();
		tc.setRailway("CN");
		TrainCrossing created = trainCrossingService.createTrainCrossing(tc);
		trainCrossingIds.add(created.getId());
		TrainCrossing added = trainAlertService.markTrainCrossingAsAlert(user, created.getId(), null);
		Assert.assertEquals(created.getId(), added.getId());
		Page<TrainAlert> alerts = trainAlertService.findUserTrainCrossingAlertPreferences(user.getId(), new PageRequest(0,5));
		Assert.assertEquals(1, alerts.getContent().size());
	}

}
