/**
 * 
 */
package cn.trace.thrift.netty.transport;

import java.net.SocketAddress;

/**
 * @author trace
 *
 */
public interface TNettyTransportContext {

	public SocketAddress getRemoteAddress();
}
