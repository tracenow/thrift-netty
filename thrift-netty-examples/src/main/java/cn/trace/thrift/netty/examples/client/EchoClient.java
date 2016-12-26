package cn.trace.thrift.netty.examples.client;

import java.net.InetSocketAddress;

import cn.trace.thrift.netty.client.pool.ThriftClientPool;
import cn.trace.thrift.netty.client.pool.ThriftClientConfig;
import cn.trace.thrift.netty.common.pool.Pool;
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
	    ThriftClientConfig poolConfig = new ThriftClientConfig();
		InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8080);
		Pool<Echo> pool = new ThriftClientPool<Echo>(poolConfig, socketAddress, Echo.class);
	    Echo client = null;
	    try {
	    	client = pool.getResource();
	    	System.out.println(client.echo("Hello World"));
	    } catch(Throwable t) {
	        pool.returnBrokenResource(client);
	        client = null;
	    } finally {
	        pool.returnResource(client);
	    }
	    pool.close();
	}

}
