package com.nanlabs.images;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public class URLConnectionFactoryPool extends GenericObjectPool<URLConnectionFactory> {


	public URLConnectionFactoryPool(int poolSize, PoolableObjectFactory<URLConnectionFactory> factory) {
		super(factory);
		this.setMaxActive(poolSize);
	}
}