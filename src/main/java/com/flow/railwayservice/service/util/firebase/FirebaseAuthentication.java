package com.flow.railwayservice.service.util.firebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;

public class FirebaseAuthentication {

	private static String databaseEndpoint;
	private static FirebaseOptions options;
	private static final String APP_NAME = "trainalert";
	static {
		// Load in database endpoint 
		Properties props = new Properties();
		try {
			InputStream stream = FirebaseMobilePush.class.getResourceAsStream("/FCMCredentials.properties");
			props.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		databaseEndpoint = props.getProperty("FCMDatabaseUrl");
		
		InputStream serviceAccount = FirebaseMobilePush.class.getResourceAsStream("/serviceAccountKey.json");
		options = new FirebaseOptions.Builder()
				  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
				  .setDatabaseUrl(databaseEndpoint)
				  .build();
		
		List<FirebaseApp> apps = FirebaseApp.getApps();
		if(apps.isEmpty()){
			FirebaseApp.initializeApp(options, APP_NAME);	
		}
		else if (!apps.contains(FirebaseApp.getInstance(APP_NAME))){
			FirebaseApp.initializeApp(options, APP_NAME);	
		}
	}
	
	/**
	 * Create custom token
	 * @param uid
	 * @return
	 */
	public static String createCustomFirebaseToken(){
	    UUID uid = UUID.randomUUID();
		Task<String> authTask = FirebaseAuth.getInstance(FirebaseApp.getInstance(APP_NAME)).createCustomToken(uid.toString());
		try {
		    Tasks.await(authTask);
		} catch (ExecutionException | InterruptedException e ) {
		    
		}
		String customToken = authTask.getResult();
		return customToken;
	}

}
