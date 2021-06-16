package com.xlhj.sharding.controller;

import com.xlhj.sharding.entity.Course;
import com.xlhj.sharding.mapper.CourseMapper;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @description:
 * @author: Han LiDong
 * @create: 2021/5/27 11:46
 * @update: 2021/5/27 11:46
 */
@RestController
@RequestMapping("/sharding")
public class ShardingController {

    @Autowired
    private CourseMapper courseMapper;

    /**
     * 分库：分布式事务
     */
    @Transactional
    @ShardingTransactionType(TransactionType.XA)  // 支持TransactionType.LOCAL, TransactionType.XA, TransactionType.BASE
    @GetMapping("/trans")
    public void trans(){
        Course course = new Course();
        course.setName("java");
        course.setStatus(0);
        course.setCreateTime(new Date());
        courseMapper.insert(course);
        Course course1 = new Course();
        course1.setName("java");
        course1.setStatus(1);
        course1.setCreateTime(new Date());
        courseMapper.insert(course1);
        //模拟报错
        int b = 1/0;
    }
}
