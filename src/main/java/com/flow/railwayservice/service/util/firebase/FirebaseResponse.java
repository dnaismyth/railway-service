package com.flow.railwayservice.service.util.firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FirebaseResponse {

    private String message_id;
    private String error;
    
    public FirebaseResponse(){}

    public String getMessage_Id(){
    	return message_id;
    }
    
    public void setMessage_Id(String message_id){
    	this.message_id = message_id;
    }
    
    public String getError(){
    	return error;
    }
    
    public void setError(String error){
    	this.error = error;
    }

   
}
