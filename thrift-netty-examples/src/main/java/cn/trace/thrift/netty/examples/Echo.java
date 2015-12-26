/**
 * 
 */
package cn.trace.thrift.netty.examples;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

/**
 * @author trace
 *
 */
@ThriftService
public interface Echo {
	
	@ThriftMethod
	public String echo(String info);
}
