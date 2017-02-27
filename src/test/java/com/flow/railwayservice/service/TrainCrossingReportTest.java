package com.flow.railwayservice.service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.flow.railwayservice.dto.Location;
import com.flow.railwayservice.dto.TrainCrossing;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.dto.UserRole;
import com.flow.railwayservice.exception.BadRequestException;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.repository.TrainCrossingReportRepository;
import com.flow.railwayservice.service.util.TimeUtil;

public class TrainCrossingReportTest extends BaseServiceTest {
	
	@Autowired
	private TrainCrossingReportService reportService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private TrainCrossingService crossingService;
	
	@Autowired
	private TrainCrossingReportRepository reportRepo;

	private String userName = "testreporter@flow.com";
	private User user;

	private List<Long> trainCrossingIds;
	
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
		reportRepo.deleteAll();
		for(Long id : trainCrossingIds){
			crossingService.deleteTrainCrossing(id);
		}
		
		userService.deleteUser(userName);
	}
	
	@Test
	public void testMakeTrainReport() throws ResourceNotFoundException{
		Location location = new Location();
		location.setAddress("124 Street");
		TrainCrossing tc = new TrainCrossing();
		tc.setRailway("CN");
		tc.setLocation(location);
		TrainCrossing created = crossingService.createTrainCrossing(tc);
		trainCrossingIds.add(created.getId());
		boolean reported = reportService.makeTrainCrossingReport(user.getId(), created.getId());
		Assert.assertTrue(reported);
	}
	
	@Test
	public void testMakeTrainReportForPreviouslyReportedCrossing() throws ResourceNotFoundException{
		Location location = new Location();
		location.setAddress("124 Street");
		TrainCrossing tc = new TrainCrossing();
		tc.setRailway("CN");
		tc.setLocation(location);
		TrainCrossing created = crossingService.createTrainCrossing(tc);
		trainCrossingIds.add(created.getId());
		boolean reported = reportService.makeTrainCrossingReport(user.getId(), created.getId());
		Assert.assertTrue(reported);
		boolean reportAgain = reportService.makeTrainCrossingReport(user.getId(), created.getId());
		Assert.assertFalse(reportAgain);
	}
	
	@Test
	public void testTimeDifference() throws InterruptedException{
		ZonedDateTime previous = ZonedDateTime.now();
		Thread.sleep(500);
		ZonedDateTime now = ZonedDateTime.now();
		long difference = TimeUtil.getZonedDateTimeDifference(now, previous, ChronoUnit.MILLIS);
		Assert.assertTrue(difference >= 500);
	}
}
