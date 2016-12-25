package cn.trace.thrift.netty.common.pool;

/**
 * @author trace
 *
 */
public class ConnectionException extends RuntimeException {
	private static final long serialVersionUID = -2308082227822377387L;

	public ConnectionException(String message) {
		super(message);
	}

	public ConnectionException(Throwable e) {
		super(e);
	}

	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}