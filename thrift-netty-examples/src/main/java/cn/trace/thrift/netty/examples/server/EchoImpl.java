package cn.trace.thrift.netty.examples.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.trace.thrift.netty.examples.api.Echo;
import cn.trace.thrift.netty.server.context.ThreadContext;
import cn.trace.thrift.netty.server.transport.TNettyTransportContext;

/**
 * @author trace
 *
 */
public class EchoImpl implements Echo {

	private static final Logger logger = LoggerFactory.getLogger(EchoImpl.class);

	@Override
	public String echo(String info) { 
		TNettyTransportContext context = ThreadContext.getTransportContext();
		logger.info("Echo from " + context.getRemoteAddress());
		return info;
	}

	@Override
	public void close() throws Exception {
		//close some connection resource
	}

}
