/**
 * 
 */
package cn.trace.thrift.netty.configure;

import java.util.concurrent.Executor;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TProtocolFactory;

/**
 * @author trace
 *
 */
public class ThriftNettyServerDef {
	
	private final String name;
    private final int serverPort;
    private final int maxFrameSize;
    private final Executor workExecutor;
    private final int bossEventLoopCount;
    private final int workerEventLoopCount;
    private final TProcessorFactory processorFactory;
    private final TProtocolFactory protocolFactory;
    
    
    public ThriftNettyServerDef(String name,
                                int serverPort,
                                int maxFrameSize,
                                Executor workExecutor,
                                int bossEventLoopCount,
                                int workerEventLoopCount,
                                TProcessorFactory processorFactory,
                                TProtocolFactory protocolFactory) {
        this.name = name;
        this.serverPort = serverPort;
        this.maxFrameSize = maxFrameSize;
        this.workExecutor = workExecutor;
        this.bossEventLoopCount = bossEventLoopCount;
        this.workerEventLoopCount = workerEventLoopCount;
        this.processorFactory = processorFactory;
        this.protocolFactory = protocolFactory;
    }

    public String getName() {
        return name;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getMaxFrameSize() {
        return maxFrameSize;
    }

    public Executor getWorkExecutor() {
        return workExecutor;
    }

    public int getBossEventLoopCount() {
        return bossEventLoopCount;
    }

    public int getWorkerEventLoopCount() {
        return workerEventLoopCount;
    }

    public TProcessorFactory getProcessorFactory() {
        return processorFactory;
    }

    public TProtocolFactory getProtocolFactory() {
        return protocolFactory;
    }
}
