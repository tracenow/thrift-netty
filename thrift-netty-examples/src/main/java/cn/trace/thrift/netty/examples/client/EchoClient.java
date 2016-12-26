package cn.trace.thrift.netty.examples.client;

import java.net.InetSocketAddress;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import cn.trace.thrift.netty.client.pool.ThriftClientPoolManager;
import cn.trace.thrift.netty.examples.api.Echo;

/**
 * @author trace
 *
 */
public class EchoClient {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8080);
		ThriftClientPoolManager<Echo> poolManager = new ThriftClientPoolManager<Echo>(poolConfig, socketAddress, Echo.class);
	    Echo client = null;
	    try {
	    	client = poolManager.getResource();
	    	System.out.println(client.echo("Hello World"));
	    } catch(Throwable t) {
	    	poolManager.returnBrokenResource(client);
	    } finally {
	    	poolManager.returnResource(client);
	    }
	    poolManager.close();
	}

}
