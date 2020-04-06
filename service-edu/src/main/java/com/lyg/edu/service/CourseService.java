package com.lyg.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyg.edu.common.R;
import com.lyg.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyg.edu.entity.CourseDetails;
import com.lyg.edu.entity.query.CourseQuery;
import com.lyg.edu.entity.query.CourseWrapper;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lyg
 * @since 2020-03-13
 */
public interface CourseService extends IService<Course> {

       void pageQuery(Page<Course> pageParam, CourseWrapper courseWrapper) ;

    R saveCourse(CourseQuery query);

    R getCourseById(String id);

    R deleteCourseById(String id);

    R updateCourseById(CourseQuery query);

    CourseDetails getCourseDetails(String id);

    R deleteCourse(String id);


}
