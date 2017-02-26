package com.flow.railwayservice.web.rest.vm;

import com.flow.railwayservice.dto.Platform;

/**
 * RestController VM to update FCM token for notifications,
 * User's device token and platform.
 * @author Dayna
 *
 */
public class TokenPlatformRequest {

	private String fcmToken;
	private Platform platform;
	private String deviceToken;
	
	public TokenPlatformRequest () {}
	
	public TokenPlatformRequest(String fcmToken, String deviceToken, Platform platform){
		this.fcmToken = fcmToken;
		this.platform = platform;
		this.deviceToken = deviceToken;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
	public String getDeviceToken(){
		return deviceToken;
	}
	
	public void setDeviceToekn(String deviceToken){
		this.deviceToken = deviceToken;
	}
	
	
}
