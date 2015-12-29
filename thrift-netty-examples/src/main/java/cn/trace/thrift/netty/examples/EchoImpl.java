/**
 * 
 */
package cn.trace.thrift.netty.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.trace.thrift.netty.transport.TNettyTransportContext;
import cn.trace.thrift.netty.util.ThreadContext;

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

}
