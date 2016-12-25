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
public class ThriftClientFactory implements PooledObjectFactory<AutoCloseable> {

	private final ThriftClientManager clientManager;
	private final InetSocketAddress socketAddress;
	private final Class<? extends AutoCloseable> type;
	
	public ThriftClientFactory(ThriftClientManager clientManager, InetSocketAddress socketAddress, Class<? extends AutoCloseable> type) {
		this.clientManager = clientManager;
		this.socketAddress = socketAddress;
		this.type = type;
	}

	@Override
	public void activateObject(PooledObject<AutoCloseable> pooledObject) throws Exception {
		//
	}

	@Override
	public void destroyObject(PooledObject<AutoCloseable> pooledObject) throws Exception {
		pooledObject.getObject().close();
	}

	@Override
	public PooledObject<AutoCloseable> makeObject() throws Exception {
		FramedClientConnector connector = new FramedClientConnector(socketAddress);
		return new DefaultPooledObject<AutoCloseable>(clientManager.createClient(connector, type).get());
	}

	@Override
	public void passivateObject(PooledObject<AutoCloseable> pooledObject) throws Exception {
		//
	}

	@Override
	public boolean validateObject(PooledObject<AutoCloseable> pooledObject) {
		return true;
	}
	

}
