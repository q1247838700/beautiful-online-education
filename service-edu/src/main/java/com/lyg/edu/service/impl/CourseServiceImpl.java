package com.lyg.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyg.edu.common.R;
import com.lyg.edu.entity.Course;
import com.lyg.edu.entity.CourseDescription;
import com.lyg.edu.entity.query.CourseQuery;
import com.lyg.edu.entity.query.CourseWrapper;
import com.lyg.edu.mapper.CourseMapper;
import com.lyg.edu.service.CourseDescriptionService;
import com.lyg.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-03-13
 */
@Service
@Transactional
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseDescriptionService service;

    @Override
    public void pageQuery(Page<Course> pageParam, CourseWrapper courseWrapper) {


        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseWrapper == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseWrapper.getTitle();
        String teacherId = courseWrapper.getTeacherId();
        String parentSubjectId = courseWrapper.getParentSubjectId();
        String subjectId = courseWrapper.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(parentSubjectId)) {
            queryWrapper.eq("parent_subject_id", parentSubjectId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }


    /**
     * 保存数据
     *
     * @param query
     * @return
     */
    @Override
    public R saveCourse(CourseQuery query) {
        if (query == null) {
            return R.error().message("没有数据");
        }
        //保存course
        Course course = new Course();
        BeanUtils.copyProperties(query, course);
        baseMapper.insert(course);
        BeanUtils.copyProperties(course, query);


        //将courseDescription保存起来
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(query.getDescription());
        service.save(courseDescription);

        return R.ok().data("course", query);

    }

    @Override
    public R getCourseById(String id) {

        CourseQuery query = new CourseQuery();

        Course course = baseMapper.selectById(id);

        BeanUtils.copyProperties(course, query);

        CourseDescription description = service.getById(id);
        if (description != null) {
            BeanUtils.copyProperties(description, query);
        }

        return R.ok().data("course", query);

    }

    @Override
    public R deleteCourseById(String id) {

        //1.删除数据库course表该信息
        int i = baseMapper.deleteById(id);
        //2.删除数据库courseDescription表该信息
        boolean flag = service.removeById(id);
        if (i > 0 && flag == true) {
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");

    }

    /**
     * 修改id
     *
     * @param query
     * @return
     */
    @Override
    public R updateCourseById(CourseQuery query) {
        if (query == null) {
            return R.error().message("没有数据");
        }
        String description = query.getDescription();


        //判断描述是否为空,

        //为空,修改course即可
        Course course = new Course();
        BeanUtils.copyProperties(query, course);
        baseMapper.updateById(course);

        //将courseDescription保存起来
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(query.getId());
        courseDescription.setDescription(description);
        service.updateById(courseDescription);

        return R.ok().data("course", query);
    }
}
