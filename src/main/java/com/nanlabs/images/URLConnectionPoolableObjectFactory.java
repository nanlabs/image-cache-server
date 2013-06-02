package com.nanlabs.images;

import org.apache.commons.pool.PoolableObjectFactory;

public class URLConnectionPoolableObjectFactory implements PoolableObjectFactory<URLConnectionFactory>{

	@Override
	public URLConnectionFactory makeObject() throws Exception {
		return new URLConnectionFactory();
	}

	@Override
	public void destroyObject(URLConnectionFactory obj) throws Exception {
	}

	@Override
	public boolean validateObject(URLConnectionFactory obj) {
		return false;
	}

	@Override
	public void activateObject(URLConnectionFactory obj) throws Exception {
	}

	@Override
	public void passivateObject(URLConnectionFactory obj) throws Exception {
	}
	
}
