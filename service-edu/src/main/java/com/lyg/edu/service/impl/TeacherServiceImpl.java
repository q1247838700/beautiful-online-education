package com.lyg.edu.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyg.edu.entity.Course;
import com.lyg.edu.entity.Teacher;
import com.lyg.edu.entity.query.TeacherQuery;
import com.lyg.edu.mapper.TeacherMapper;
import com.lyg.edu.service.CourseService;
import com.lyg.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-03-03
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    private CourseService courseService;

    @Override
    @SentinelResource(value = "pageQUery")
    public void pageQUery(Page<Teacher> teacherPage, TeacherQuery teacher) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();

        if (teacher == null || "".equals(teacher)) {
            baseMapper.selectPage(teacherPage, null);
            return;
        }
        String name = teacher.getName();
        String level = teacher.getLevel();
        String begin = teacher.getBegin();
        String end = teacher.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        if (!StringUtils.isEmpty(begin)) {
//            try {
//                sdf.parse(begin);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
//            try {
//                sdf.parse(end);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            wrapper.le("gmt_create", end);
        }

        baseMapper.selectPage(teacherPage, wrapper);
    }

    @Override
    public Map<String, Object> getTeacherAndCourseById(String id) {
        //1.根据传来的教师id查询教师和教师所交的课程
        //先查教师
        Teacher teacher = baseMapper.selectById(id);
        //再查所有课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<Course> courses = courseService.list(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacher", teacher);
        map.put("courses", courses);
        return map;
    }
}
