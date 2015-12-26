/**
 * 
 */
package cn.trace.thrift.netty;

import java.util.concurrent.Executor;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import cn.trace.thrift.netty.configure.ThriftNettyServerDef;
import cn.trace.thrift.netty.transport.TNettyTransport;
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
		this.executor = def.getWorkExecutor();
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
                    TProtocol inProtocol = inProtocolFactory.getProtocol(messageTransport);
                    TProtocol outProtocol = outProtocolFactory.getProtocol(messageTransport);
                    ListenableFuture<Boolean> processFuture =
                            Futures.immediateFuture(processorFactory.getProcessor(messageTransport).process(inProtocol, outProtocol));
                    Futures.addCallback(processFuture, new FutureCallback<Boolean>() {

                        @Override
                        public void onSuccess(Boolean result) {
                            if (ctx.channel().isActive()) {
                                ctx.writeAndFlush(messageTransport);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            ctx.close();
                        }

                    });
                } catch (Throwable cause) {
                    ctx.fireExceptionCaught(cause);
                }
            }
            
        });
        
    }
}
