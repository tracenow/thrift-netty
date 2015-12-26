/**
 * 
 */
package cn.trace.thrift.netty.examples;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.swift.service.ThriftClientManager;

/**
 * @author trace
 *
 */
public class EchoClient {

	/**
	 * @param args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThriftClientManager clientManager = new ThriftClientManager();
		try {
			FramedClientConnector connector = new FramedClientConnector(new InetSocketAddress("localhost", 8080));
			Echo client = clientManager.createClient(connector, Echo.class).get();
			for(int i = 0; i < 1000; i++) {
				System.out.println(client.echo("Hello World"));
			}
		} finally {
			clientManager.close();
		}
	}

}
