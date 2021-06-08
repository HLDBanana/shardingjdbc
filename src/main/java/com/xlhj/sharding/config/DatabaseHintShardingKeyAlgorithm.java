package com.xlhj.sharding.config;

import com.alibaba.druid.util.StringUtils;
import org.apache.shardingsphere.api.sharding.ShardingValue;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @description:
 * @author: Han LiDong
 * @create: 2021/5/27 09:53
 * @update: 2021/5/27 09:53
 */
@Component
public class DatabaseHintShardingKeyAlgorithm implements HintShardingAlgorithm {

    /**
     * 自定义Hint 实现算法
     * 能够保证绕过Sharding-JDBC SQL解析过程
     * @param availableTargetNames  数据库集合
     * @param hintShardingValue 不再从SQL 解析中获取值，而是直接通过hintManager.addTableShardingValue("t_order", 1)参数指定
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection availableTargetNames, HintShardingValue hintShardingValue) {
        System.out.println("database:shardingValue=" + hintShardingValue);
        System.out.println("database:availableTargetNames=" + availableTargetNames);

        List<String> shardingResult = new ArrayList<>();
        Iterator i = availableTargetNames.iterator();
        while (i.hasNext()){
            String targetName = (String) i.next();
            String suffix = targetName.substring(targetName.length() - 1);
            if (StringUtils.isNumber(suffix)) {
                // hint分片算法的ShardingValue有两种具体类型:
                // ListShardingValue和RangeShardingValue
                // 使用哪种取决于HintManager.addDatabaseShardingValue(String, String, ShardingOperator,...),ShardingOperator的类型
                Iterator j = hintShardingValue.getValues().iterator();
                while (j.hasNext()){
                    Integer value = (Integer) j.next();
                    if (value % 2 + 1 == Integer.parseInt(suffix)) {
                        shardingResult.add(targetName);
                    }
                }
            }
        }
        return shardingResult;
    }

}
