package cn.trace.thrift.netty.client.pool;

import java.net.InetSocketAddress;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.facebook.swift.service.ThriftClientManager;

import cn.trace.thrift.netty.common.pool.Pool;
import cn.trace.thrift.netty.common.pool.PoolException;

/**
 * @author trace
 *
 */
public class ThriftClientPool extends Pool<AutoCloseable> {

	private final ThriftClientManager clientManager = new ThriftClientManager();
	
	public ThriftClientPool(GenericObjectPoolConfig poolConfig, InetSocketAddress socketAddress,
			Class<? extends AutoCloseable> type) {
		this.internalPool = new GenericObjectPool<AutoCloseable>(new ThriftClientFactory(clientManager, socketAddress, type), poolConfig);
	}

	public void returnBrokenResource(AutoCloseable resource) {
		if (resource != null)
			returnBrokenResourceObject(resource);
	}

	public void returnResource(AutoCloseable resource) {
		if (resource != null)
			try {
				returnResourceObject(resource);
			} catch (Exception e) {
				returnBrokenResource(resource);
				throw new PoolException("Could not return the resource to the pool", e);
			}
	}

	public AutoCloseable getResource() {
		return super.getResource();
	}

	@Override
	public void close() {
		super.close();
		this.clientManager.close();
	}
	
}
