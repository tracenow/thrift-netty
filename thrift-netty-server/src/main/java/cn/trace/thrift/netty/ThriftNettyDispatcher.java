/**
 * 
 */
package cn.trace.thrift.netty;

import java.util.concurrent.Executor;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

import cn.trace.thrift.netty.configure.ThriftNettyServerDef;
import cn.trace.thrift.netty.transport.TNettyTransport;
import cn.trace.thrift.netty.util.ThreadContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author trace
 *
 */
public class ThriftNettyDispatcher extends ChannelInboundHandlerAdapter {

	private final TProcessorFactory processorFactory;

	private final TProtocolFactory inProtocolFactory;

	private final TProtocolFactory outProtocolFactory;

	private final Executor executor;

	public ThriftNettyDispatcher(ThriftNettyServerDef def) {
		this.processorFactory = def.getProcessorFactory();
		this.inProtocolFactory = def.getProtocolFactory();
		this.outProtocolFactory = def.getProtocolFactory();
		this.executor = def.getTaskExecutor();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof TNettyTransport) {
			processRequest(ctx, (TNettyTransport) msg);
		} else {
			super.channelRead(ctx, msg);
		}
	}

	private void processRequest(final ChannelHandlerContext ctx, final TNettyTransport messageTransport)
			throws Exception {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					ThreadContext.bind(messageTransport);
					TProtocol inProtocol = inProtocolFactory.getProtocol(messageTransport);
					TProtocol outProtocol = outProtocolFactory.getProtocol(messageTransport);
					processorFactory.getProcessor(messageTransport).process(inProtocol, outProtocol);
					ctx.writeAndFlush(messageTransport);
				} catch (Throwable cause) {
					ctx.fireExceptionCaught(cause);
				} finally {
					ThreadContext.unbind();
				}
			}

		});
	}

}
