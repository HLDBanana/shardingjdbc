package com.xlhj.sharding.config;

import com.xlhj.sharding.util.DateUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

/**
 * @description: 精准分片算法类
 * @author: Han LiDong
 * @create: 2021/5/25 10:32
 * @update: 2021/5/25 10:32
 */
@Component
public class CoursePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {


    /**
     * 按照 tablename_yyyyMM进行分表
     * @param collection            分表列表
     * @param preciseShardingValue  分片键 值
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        StringBuffer tableName = new StringBuffer();
        tableName.append(preciseShardingValue.getLogicTableName())
                .append("_").append(DateUtil.dateToString(preciseShardingValue.getValue(),DateUtil.PATTERN_DATE3));
        return tableName.toString();
    }

}
