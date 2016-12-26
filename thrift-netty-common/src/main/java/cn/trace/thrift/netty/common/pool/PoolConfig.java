package cn.trace.thrift.netty.common.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author trace
 * 
 */
public class PoolConfig {

    private GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    
    public GenericObjectPoolConfig getPoolConfig() {
        return poolConfig;
    }

    public PoolConfig poolConfig(GenericObjectPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
        return this;
    }
    
}
