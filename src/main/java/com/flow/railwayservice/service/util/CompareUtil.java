package com.flow.railwayservice.service.util;

import java.util.List;

public abstract class CompareUtil {

	public static boolean compare(Object obj1, Object obj2){
		if(obj1 == null){
			return false;
		}
		if(!obj1.equals(obj2)){
			return false;
		}
		return true;
	}
	
	public static <T> boolean compare(List<T> obj1, List<T> obj2){
		if(obj1 == null){
			return false;
		}
		if(!obj1.equals(obj2)){
			return false;
		}
		return true;
	}
	
	public static boolean compare(String s1, String s2){
		
		if(s1 == null){
			return false;
		}
		else if(!s1.equals(s2)){
			return false;
		}
		return true;
	}
}
