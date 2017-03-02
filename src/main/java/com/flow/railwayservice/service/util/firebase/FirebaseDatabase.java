package com.flow.railwayservice.service.util.firebase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;


public class FirebaseDatabase {
	private static final Logger log = LoggerFactory.getLogger(FirebaseDatabase.class);

	
	private static String databaseEndpoint;
	private static String databaseSecret;

	static {
		log.debug("==================Initializing Database Endpoint================== ");
		Properties props = new Properties();
		try {
			InputStream stream = FirebaseMobilePush.class.getResourceAsStream("/FCMCredentials.properties");
			props.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		databaseEndpoint = props.getProperty("FCMDatabaseUrl");
		databaseSecret = props.getProperty("databaseSecret");
		log.debug("The endpoint is: {}", databaseEndpoint);
		log.debug("==================Begin Updating Data================== ");

	}
	
	/**
	 * Update Train crossing
	 * @param entity
	 * @return
	 */
	@Async
	public static CompletableFuture<FirebaseTrainCrossingModel> requestModelUpdate(HttpEntity<FirebaseTrainCrossingModel> entity, String url) {
		log.debug("Built url is:{}", url);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<FirebaseTrainCrossingModel> updated = null;
		try {
			updated = restTemplate.exchange(url, HttpMethod.PUT, entity, FirebaseTrainCrossingModel.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CompletableFuture.completedFuture(updated.getBody());
	}
	
	private static String buildTrainCrossingEndpoint(Long trainCrossingId){
		return databaseEndpoint.concat("traincrossing/traincrossing_").concat(trainCrossingId.toString().concat(".json")
				.concat("?auth=".concat(databaseSecret)));
	}
	
	public static ResponseEntity<String> updateTrainCrossing(Long trainCrossingId, Boolean isActive, Integer notificationCount){
		String url = buildTrainCrossingEndpoint(trainCrossingId);
		HttpHeaders headers = buildHeaders();
		FirebaseTrainCrossingModel updateModel = buildTrainCrossingModel(isActive, notificationCount);
		HttpEntity<FirebaseTrainCrossingModel> request = new HttpEntity<FirebaseTrainCrossingModel>(updateModel, headers);
		JSONObject obj = new JSONObject(request);
		log.info(obj.toString());
		try {
			CompletableFuture<FirebaseTrainCrossingModel> trainCrossingUpdate = requestModelUpdate(request, url);
			CompletableFuture.allOf(trainCrossingUpdate).join();
			FirebaseTrainCrossingModel response = trainCrossingUpdate.get();
			if (response.getNotification_count().equals(notificationCount) && response.getIs_active().equals(isActive)) {
				log.info("Successfully updated Train Crossing: {}", trainCrossingId);
			} else {
				log.error("Error updating train crossing");
			}
			return new ResponseEntity<>(trainCrossingUpdate.toString(), HttpStatus.OK);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Update could not be processed.", HttpStatus.BAD_REQUEST);
	
	}
	
	private static HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return headers;
	}
	
	private static FirebaseTrainCrossingModel buildTrainCrossingModel(Boolean isActive, Integer notificationCount){
		FirebaseTrainCrossingModel model = new FirebaseTrainCrossingModel();
		model.setIs_active(isActive);
		model.setNotification_count(notificationCount);
		return model;
	}
	
	/**
	 * Class to represent the firebase train crossing model for
	 * Real time data.
	 * @author Dayna
	 *
	 */
	public static class FirebaseTrainCrossingModel{
		
		private Boolean is_active;
		private Integer notification_count;
		
		public FirebaseTrainCrossingModel(){}
		
		public Boolean getIs_active(){
			return is_active;
		}
		
		public void setIs_active(Boolean is_active){
			this.is_active = is_active;
		}
		
		public Integer getNotification_count(){
			return notification_count;
		}
		
		public void setNotification_count(Integer notification_count){
			this.notification_count = notification_count;
		}	
	}
	
}


