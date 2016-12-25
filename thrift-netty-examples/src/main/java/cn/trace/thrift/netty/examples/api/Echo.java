package cn.trace.thrift.netty.examples.api;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

/**
 * @author trace
 *
 */
@ThriftService
public interface Echo extends AutoCloseable {

	@ThriftMethod
	public String echo(String info);
}
