package cn.trace.thrift.netty.client.pool;

import java.net.InetSocketAddress;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author trace
 *
 */
public class ThriftClientPoolManager<T extends AutoCloseable> {

	private final ThriftClientPool pool;
	
	public ThriftClientPoolManager(GenericObjectPoolConfig poolConfig, InetSocketAddress socketAddress,
			Class<T> type) {
		this.pool = new ThriftClientPool(poolConfig, socketAddress, type);
	}
	
	public void returnBrokenResource(T resource) {
		pool.returnBrokenResource(resource);
	}

	public void returnResource(T resource) {
		pool.returnResource(resource);
	}

	@SuppressWarnings("unchecked")
	public T getResource() {
		return (T)pool.getResource();
	}

	public void close() {
		pool.close();
	}
	
}
