package com.flow.railwayservice.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;

import com.flow.railwayservice.service.util.notification.FirebaseMobilePush;

@Ignore
public class PushNotificationTest {

	@Test
	public void testSendNotification() throws JSONException{
		String topic = "pittriver";
		FirebaseMobilePush.sendNotification(topic, "Pitt River");
	}
}
