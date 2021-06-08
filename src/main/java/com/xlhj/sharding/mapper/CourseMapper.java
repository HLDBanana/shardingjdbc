package com.xlhj.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xlhj.sharding.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Han LiDong
 * @since 2021-05-26
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {

//    public int insert(Course course);

    /**
     * 范围查询
     * @return
     */
    public List<Course> rangData();

    /**
     * 范围查询
     * @return
     */
    public List<Course> binding(List<Long> ids);

    /**
     * 函数测试
     * @return
     */
    public Integer avgVal();

}
