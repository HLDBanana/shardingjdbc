package com.xlhj.sharding.config;

import com.google.common.collect.Range;
import com.xlhj.sharding.util.DateUtil;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 范围分片算法类
 * @author: Han LiDong
 * @create: 2021/5/25 10:32
 * @update: 2021/5/25 10:32
 */
@Component
public class TableRangeShardAlgorithm implements RangeShardingAlgorithm<Date> {


    private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 分片键日期范围包含分片表名称集合
     * @param availableTargetNames
     * @param rangeShardingValue
     * @return
     */
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> rangeShardingValue) {
        System.out.println("范围-*-*-*-*-*-*-*-*-*-*-*---------------" + availableTargetNames);
        System.out.println("范围-*-*-*-*-*-*-*-*-*-*-*---------------" + rangeShardingValue);
        //物理表名集合
        //Collection<String> tables = new LinkedHashSet<>();
        //逻辑表名
        String logicTableName = rangeShardingValue.getLogicTableName();
        //分片键的值
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        Date lowerEndpoint = valueRange.lowerEndpoint();

        Date upperEndpoint = valueRange.upperEndpoint();
        List<String> YMList = DateUtil.getYMBetweenDate(lowerEndpoint,upperEndpoint);
        List<String> tables = YMList.stream().map( ym ->{
            return logicTableName + "_" + ym;
        }).collect(Collectors.toList());
        return tables;
    }

}