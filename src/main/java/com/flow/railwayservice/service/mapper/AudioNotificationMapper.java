package com.flow.railwayservice.service.mapper;

import com.flow.railwayservice.domain.RAudioNotification;
import com.flow.railwayservice.dto.AudioNotification;

/**
 * Helper class to map AudioNotification
 * @author Dayna
 *
 */
public class AudioNotificationMapper {

	/**
	 * To AudioNotification DTO
	 * @param ra
	 * @return
	 */
	public AudioNotification toAudioNotification(RAudioNotification ra){
		
		AudioNotification an = null;
		if(ra != null){
			an = new AudioNotification();
			an.setFileName(ra.getFileName());
			an.setId(ra.getId());
			an.setUrl(ra.getFileName());		// TODO: generate url to storage
		}
		return an;
	}
	
	/**
	 * To AudioNotification Entity
	 * @param an
	 * @return
	 */
	public RAudioNotification toRAudioNotification(AudioNotification an){
		
		RAudioNotification ra = null;
		if(an != null){
			ra = new RAudioNotification();
			ra.setFileName(an.getFileName());
			ra.setId(an.getId());
		}
		
		return ra;
	}
}
