package com.xlhj.sharding.config;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @description: 分表算法类-多分片键
 * @author: Han LiDong
 * @create: 2021/5/25 10:32
 * @update: 2021/5/25 10:32
 */
//ComplexKeysShardingAlgorithm
@Component
public class CourseShardingAlgorithmColumns implements ComplexKeysShardingAlgorithm  {


    /**
     *
     * @param collection        分片表名
     * @param shardingValues    分片字段值
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue shardingValues) {
        System.out.println("collection:" + collection + ",shardingValues:" + shardingValues);
        Map<String, Collection> map = shardingValues.getColumnNameAndShardingValuesMap();
        Collection<Long> idValues = map.get("id");
        Collection<Integer> statusValues = map.get("status");
        List<String> shardingSuffix = new ArrayList<>();
        //逻辑还是按照 id%2 + 1进行数据分片
        for (Long id : idValues) {
            Long suf = id % 2 + 1;
            for (Object s : collection) {
                String tableName = (String) s;
                // 分片表名后缀匹配
                if (tableName.endsWith(String.valueOf(suf))) {
                    shardingSuffix.add(tableName);
                }
            }
        }
        return shardingSuffix;
    }
}
