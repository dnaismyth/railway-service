package com.flow.railwayservice.service.util;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

	public static ZonedDateTime getCurrentTime(){
		return ZonedDateTime.now();
	}
	
	public static long getZonedDateTimeDifference(ZonedDateTime currentTime, ZonedDateTime pastTime, ChronoUnit unit){
		return unit.between(pastTime, currentTime);
	}
}
