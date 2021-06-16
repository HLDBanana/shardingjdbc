package com.xlhj.sharding.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: 自定义sharding-jdbc主键生成策略
 * @author: Han LiDong
 * @create: 2021/5/25 09:36
 * @update: 2021/5/25 09:36
 */
@Component
public class SimpleShardingKeyGenerator implements ShardingKeyGenerator {

    private AtomicLong atomic = new AtomicLong(0);

    @Getter
    @Setter
    private Properties properties = new Properties();

    /**
     * 主键生成算法实现
     * @return
     */
    @Override
    public Comparable<?> generateKey() {
        return atomic.incrementAndGet();
    }

    @Override
    public String getType() {
        //声明类型
        return "SIMPLE";
    }
}
