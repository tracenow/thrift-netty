package cn.trace.thrift.netty.client.pool;

import java.net.InetSocketAddress;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.swift.service.ThriftClientManager;

/**
 * @author trace
 *
 */
public class ThriftClientFactory<T extends AutoCloseable> implements PooledObjectFactory<T> {

	private final ThriftClientManager clientManager;
	private final InetSocketAddress socketAddress;
	private final Class<T> type;
	
	public ThriftClientFactory(ThriftClientManager clientManager, InetSocketAddress socketAddress, Class<T> type) {
		this.clientManager = clientManager;
		this.socketAddress = socketAddress;
		this.type = type;
	}

	@Override
	public void activateObject(PooledObject<T> pooledObject) throws Exception {
		//
	}

	@Override
	public void destroyObject(PooledObject<T> pooledObject) throws Exception {
		pooledObject.getObject().close();
	}

    @Override
	public PooledObject<T> makeObject() throws Exception {
		FramedClientConnector connector = new FramedClientConnector(socketAddress);
		return new DefaultPooledObject<T>(clientManager.createClient(connector, type).get());
	}

	@Override
	public void passivateObject(PooledObject<T> pooledObject) throws Exception {
		//
	}

	@Override
	public boolean validateObject(PooledObject<T> pooledObject) {
		return true;
	}
	

}
