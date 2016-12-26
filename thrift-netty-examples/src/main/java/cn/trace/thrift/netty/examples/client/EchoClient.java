package cn.trace.thrift.netty.examples.client;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

import cn.trace.thrift.netty.client.pool.ThriftClientConfig;
import cn.trace.thrift.netty.client.pool.ThriftClientPool;
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
	    ThriftClientConfig config = new ThriftClientConfig();
		InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8080);
		int count = 100;
		final Pool<Echo> pool = new ThriftClientPool<Echo>(config, socketAddress, Echo.class);
		final CountDownLatch latch = new CountDownLatch(count);
		for(int i = 0; i < count; i++) {
		    new Thread(new Runnable(){

                @Override
                public void run() {
                    Echo client = null;
                    try {
                        client = pool.getResource();
                        System.out.println(client.echo("Hello World"));
                    } catch(Throwable t) {
                        pool.returnBrokenResource(client);
                        client = null;
                    } finally {
                        pool.returnResource(client);
                        latch.countDown();
                    }
                }
		        
		    }).start();
		}
		latch.await();
	    pool.close();
	}

}
