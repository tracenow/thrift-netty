/**
 * 
 */
package cn.trace.thrift.netty.codec;

import cn.trace.thrift.netty.transport.TNettyTransport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author trace
 *
 */
public abstract class AbstractThriftNettyEncoder extends MessageToByteEncoder<TNettyTransport> {

	@Override
	protected void encode(ChannelHandlerContext ctx, TNettyTransport msg, ByteBuf out) throws Exception {
		ByteBuf message = encode(ctx, msg);
		try {
			out.writeBytes(message);
		} finally {
			message.release();
		}
	}

	protected abstract ByteBuf encode(ChannelHandlerContext ctx, TNettyTransport msg) throws Exception;
}
