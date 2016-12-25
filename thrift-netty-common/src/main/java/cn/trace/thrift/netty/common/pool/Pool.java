package cn.trace.thrift.netty.common.pool;

import java.io.Closeable;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author trace
 *
 */
public abstract class Pool<T> implements Closeable {

	protected GenericObjectPool<T> internalPool;

	public Pool() {
	}

	public void close() {
		closeInternalPool();
	}

	public boolean isClosed() {
		return this.internalPool.isClosed();
	}

	public Pool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
		initPool(poolConfig, factory);
	}

	public void initPool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
		if (this.internalPool != null)
			try {
				closeInternalPool();
			} catch (Exception e) {
			}
		this.internalPool = new GenericObjectPool<T>(factory, poolConfig);
	}

	public T getResource() {
		try {
			return this.internalPool.borrowObject();
		} catch (Exception e) {
			throw new ConnectionException("Could not get a resource from the pool", e);
		}
	}

	public void returnResourceObject(T resource) {
		if (resource == null)
			return;
		try {
			this.internalPool.returnObject(resource);
		} catch (Exception e) {
			throw new PoolException("Could not return the resource to the pool", e);
		}
	}

	public void returnBrokenResource(T resource) {
		if (resource != null)
			returnBrokenResourceObject(resource);
	}

	public void returnResource(T resource) {
		if (resource != null)
			returnResourceObject(resource);
	}

	public void destroy() {
		closeInternalPool();
	}

	protected void returnBrokenResourceObject(T resource) {
		try {
			this.internalPool.invalidateObject(resource);
		} catch (Exception e) {
			throw new PoolException("Could not return the resource to the pool", e);
		}
	}

	protected void closeInternalPool() {
		try {
			this.internalPool.close();
		} catch (Exception e) {
			throw new PoolException("Could not destroy the pool", e);
		}
	}
}
