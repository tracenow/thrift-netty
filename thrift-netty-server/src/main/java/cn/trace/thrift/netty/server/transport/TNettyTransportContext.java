package cn.trace.thrift.netty.server.transport;

import java.net.SocketAddress;

/**
 * @author trace
 *
 */
public interface TNettyTransportContext {

	public SocketAddress getRemoteAddress();
}
