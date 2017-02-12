package com.flow.railwayservice.web.rest.vm;

import java.util.Collection;

/**
 * Response VM for Collections
 * @author Dayna
 *
 * @param <T>
 */
public class CollectionResponse<T> {

	private Collection<T> data;
	private int count;
	
	public CollectionResponse(Collection<T> data){
		this.data = data;
		this.count = data.size();
	}

	public Collection<T> getData() {
		return data;
	}

	public void setData(Collection<T> data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
