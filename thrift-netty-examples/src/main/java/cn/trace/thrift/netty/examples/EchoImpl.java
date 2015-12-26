/**
 * 
 */
package cn.trace.thrift.netty.examples;

/**
 * @author trace
 *
 */
public class EchoImpl implements Echo {

	@Override
	public String echo(String info) {
		return info;
	}
	
}
