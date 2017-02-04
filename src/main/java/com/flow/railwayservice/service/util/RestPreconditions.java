package com.flow.railwayservice.service.util;

import com.flow.railwayservice.exception.BadRequestException;

public final class RestPreconditions{
	
	private RestPreconditions(){
		throw new AssertionError();
	}
	
	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not null.
	 * @param reference an object reference
	 * @return the non-null reference that was validated
	 * @throws ResourceNotFoundException if {@code reference} is null
	 */
	public static < T >T checkNotNull( final T reference ) throws IllegalArgumentException{
		if( reference == null ){
			throw new IllegalArgumentException();
		}
		return reference;
	}
	
	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not null.
	 * @param reference an object reference
	 * @return the non-null reference that was validated
	 * @throws BadRequestException if {@code reference} is null
	 */
	public static < T >T checkRequestElementNotNull( final T reference ) throws BadRequestException{
		if( reference == null ){
			throw new BadRequestException();
		}
		return reference;
	}
	
	public static void checkRequestState( final boolean expression ) throws BadRequestException{
		if( !expression ){
			throw new BadRequestException();
		}
	}

}

