package com.xlhj.sharding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xlhj.sharding.entity.*;
import com.xlhj.sharding.mapper.*;
import com.xlhj.sharding.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: lcj
 * @Date: 2020/11/2 10:53
 * @Description: 测试
 * @Version: 0.0.1
 */
@SpringBootTest
@Slf4j
public class ShardApplicationTest {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseDetailMapper courseDetailMapper;
    @Autowired
    private SysUserMapper userMapper;
//    @Autowired
//    private SysDictMapper dictMapper;
    @Autowired
    private TDictMapper dictMapper;

    /**
     * 测试分表
     */
    @Test
    public void addCourse() {
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setName("java" + i);
            course.setStatus(1);
            course.setCreateTime(new Date());
            courseMapper.insert(course);
        }
    }

    /**
     * 绑定表测试(查询笛卡尔积)
     */
    @Test
    public void bindingTest(){
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setName("java" + i);
            course.setStatus(1);
            course.setCreateTime(new Date());
            courseMapper.insert(course);
            CourseDetail courseDetail = new CourseDetail();
            //courseDetail.setCourseId(course.getId());
            courseDetail.setRemark("备注" + i);
            courseDetailMapper.insert(courseDetail);
            ids.add(course.getId());
        }
        List<Course> res = courseMapper.binding(ids);
        log.info("查询结果：{}",res.size());
    }

    /**
     * 查询分表数据
     * 测试分表字段精准查询、分表字段范围查询  非分表字段查询
     */
    @Test
    public void findCourse() {

       Course course = courseMapper.selectById(Long.valueOf("607168187053637632"));
        log.info(course.toString());

        QueryWrapper<Course> queryWrapper1 = new QueryWrapper<Course>();
        //分区字段查询数据：若id只存在于一个表中，直接去当前表拿数据，不会去别的表中扫描数据
        //queryWrapper.in("id", Arrays.asList(Long.valueOf("604299009560936448"),Long.valueOf("604299062182674432")));
        //非分区字段查询：course_2库status全部置为0， 还是会从所有表中查询数据，然后汇总结果
        queryWrapper1.eq("create_time", DateUtil.stringToDate("2021-05-26 11:39:05"));
        List<Course> list1 = courseMapper.selectList(queryWrapper1);
        log.info("数据量{}",list1.size());
        QueryWrapper<Course> queryWrapper2 = new QueryWrapper<Course>();
        //分区字段查询数据：若id只存在于一个表中，直接去当前表拿数据，不会去别的表中扫描数据
        //queryWrapper.in("id", Arrays.asList(Long.valueOf("604299009560936448"),Long.valueOf("604299062182674432")));
        //非分区字段查询：course_2库status全部置为0， 还是会从所有表中查询数据，然后汇总结果
        queryWrapper2.between("create_time",
                DateUtil.stringToDate("2021-01-26 11:39:05"),
                DateUtil.stringToDate("2021-07-26 11:39:05"));
        //queryWrapper.eq("status",1);
        List<Course> list2 = courseMapper.selectList(queryWrapper2);
        log.info("数据量{}",list2.size());

    }


    /**
     * 范围查询分表数据
     */
    @Test
    public void rangeCourse() {
        List<Course> list = courseMapper.rangData();
        log.info("数据量{}",list.size());
    }

    /**
     * 测试水平分库+分表
     */
    @Test
    public void addCourseDB() {
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setName("java");
            int rand = (int)(Math.random() * 10);
            course.setStatus(rand % 2);
            course.setCreateTime(new Date());
            courseMapper.insert(course);
        }
    }

    /**
     * 测试：更新分库字段
     */
    @Test
    public void update(){
        Course course = courseMapper.selectById(Long.valueOf("604666077854564352"));
        course.setStatus(course.getStatus() == 0 ? 1 : 0);
        course.setName("update");
        courseMapper.updateById(course);
    }

    /**
     * hint分片算法测试
     * @throws Exception
     */
    @Test
    public void shardingHintDB() throws Exception {
        HintManager.clear();
        HintManager hintManager = HintManager.getInstance();
        // 方式1:
        // 下面2句话的意思时: 向3号库中的1号 course 表执行sql
        // 选择具体的数据库, 3 可以简单理解为: 3号库,如果只有2个库, 那么可以根据2取模+1,落到 2号库上面
        hintManager.addDatabaseShardingValue("course", 3);
        // 同理:一个数据库中可以有多张courser表, 2 可以理解为: 2月份相关表.
        hintManager.addTableShardingValue("course", 2);
        // 方式2
        // 直接指定对应具体的数据库,会想此库里所有分片表添加数据
        //hintManager.setDatabaseShardingValue(0);
        Course course = new Course();
        course.setName("java");
        int rand = (int)(Math.random() * 10);
        course.setStatus(rand % 2);
        course.setCreateTime(new Date());
        courseMapper.insert(course);
        HintManager.clear();
    }
    /**
     * 测试垂直分库
     */
    @Test
    public void addUserDB() {
        SysUser user = new SysUser();
        user.setName("zhangsan");
        userMapper.insert(user);
    }

    /**
     * 测试公共表
     */
    @Test
    public void addDict() {
        TDict dict = new TDict();
        dict.setCreateTime(new Date());
        dict.setDicCode("test");
        dict.setDicName("test");
        dict.setDicSort("1");
        dict.setDicValue("test");
        dict.setPcode("0");
        dict.setStatus("1");
        dictMapper.insert(dict);
    }

    @Test
//    @Rollback(value = false)
    @Transactional
    @ShardingTransactionType(TransactionType.XA)  // 支持TransactionType.LOCAL, TransactionType.XA, TransactionType.BASE
    public void transactionTest() {
        Course course = new Course();
        course.setName("java");
        int rand = (int)(Math.random() * 10);
        course.setStatus(rand % 2);
        course.setCreateTime(new Date());
        courseMapper.insert(course);
        Course course1 = new Course();
        course1.setName("java");
        int rand1 = (int)(Math.random() * 10);
        course1.setStatus(rand1 % 2 + 1);
        course1.setCreateTime(new Date());
        courseMapper.insert(course1);
        int a = 1/0;
    }


}
