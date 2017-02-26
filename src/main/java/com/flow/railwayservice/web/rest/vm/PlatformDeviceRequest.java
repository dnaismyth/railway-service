package com.flow.railwayservice.web.rest.vm;

import com.flow.railwayservice.dto.Platform;

/**
 * Rest Controller VM to update/save device token and platform
 * @author Dayna
 *
 */
public class PlatformDeviceRequest {

	private String deviceToken;
	private Platform platform;
	
	public PlatformDeviceRequest () {}
	
	public PlatformDeviceRequest(String deviceToken, Platform platform){
		this.deviceToken = deviceToken;
		this.platform = platform;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
	
}
