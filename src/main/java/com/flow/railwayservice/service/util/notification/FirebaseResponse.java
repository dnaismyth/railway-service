package com.flow.railwayservice.service.util.notification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FirebaseResponse {

    private String message_id;

    public String getMessage_Id(){
    	return message_id;
    }
    
    public void setMessage_Id(String message_id){
    	this.message_id = message_id;
    }

   
}
