package com.flow.railwayservice.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;

import com.flow.railwayservice.service.util.firebase.FirebaseDatabase;
import com.flow.railwayservice.service.util.firebase.FirebaseMobilePush;

@Ignore
public class FirebaseRequestTests {

	@Test
	public void testSendNotification() throws JSONException{
		String topic = "pittriver";
		FirebaseMobilePush.sendNotification(topic, "Pitt River");
	}
	
	@Test
	public void testUpdateTrainCrossing(){
		Long trainCrossingId = 2305L;
		Boolean isActive = false;
		Integer notificationCount = 10;
		FirebaseDatabase.updateTrainCrossing(trainCrossingId, isActive, notificationCount);
	}
}
