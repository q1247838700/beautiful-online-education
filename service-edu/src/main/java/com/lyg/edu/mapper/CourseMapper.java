package com.lyg.edu.mapper;

import com.lyg.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyg.edu.entity.CourseDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lyg
 * @since 2020-03-13
 */
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 用来查询课程细节的
     * @param id
     * @return
     */
    CourseDetails getCourseDetails(@Param("id") String id);

    List<String> getChapterId(String id);
}
