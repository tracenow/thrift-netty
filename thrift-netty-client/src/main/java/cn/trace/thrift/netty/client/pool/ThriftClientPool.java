package cn.trace.thrift.netty.client.pool;

import java.net.InetSocketAddress;

import org.apache.commons.pool2.impl.GenericObjectPool;

import cn.trace.thrift.netty.common.pool.Pool;
import cn.trace.thrift.netty.common.pool.PoolException;

import com.facebook.swift.service.ThriftClientManager;

/**
 * @author trace
 *
 */
public class ThriftClientPool<T extends AutoCloseable> extends Pool<T> {

	private final ThriftClientManager clientManager = new ThriftClientManager();
	
	public ThriftClientPool(ThriftClientConfig poolConfig, InetSocketAddress socketAddress,
			Class<T> type) {
		this.internalPool = new GenericObjectPool<T>(
		        new ThriftClientFactory<T>(clientManager, socketAddress, type, poolConfig),
		        poolConfig.getPoolConfig());
	}

	public void returnBrokenResource(T resource) {
		if (resource != null)
			returnBrokenResourceObject(resource);
	}

	public void returnResource(T resource) {
		if (resource != null)
			try {
				returnResourceObject(resource);
			} catch (Exception e) {
				returnBrokenResource(resource);
				throw new PoolException("Could not return the resource to the pool", e);
			}
	}

    public T getResource() {
		return super.getResource();
	}

	@Override
	public void close() {
		super.close();
		this.clientManager.close();
	}
	
}
