package com.lyg.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyg.edu.common.R;
import com.lyg.edu.entity.Course;
import com.lyg.edu.entity.query.CourseWrapper;
import com.lyg.edu.entity.query.FrontCourseDetails;
import com.lyg.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author lyg
 * @create 2020-04-07-17:32
 */
@RestController
@RequestMapping("/edu/front/course")
@CrossOrigin
@Api(tags = {"前段课程接口"})
public class FrontCourseController {
    @Autowired
    private CourseService service;

    @ApiOperation("分页显示课程")
    @GetMapping("/{page}/{limit}")
    public R getCoursePages(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {


        Page<Course> page1 = service.getCourseFront(page,limit);
        return R.ok().data("pages", page1);
    }
    @ApiOperation("获得该课程细节")
    @GetMapping("/{id}")
    public R getCourseById(@PathVariable("id") String id) {
        Map<String, Object> map = service.getCourseDetailsById(id);
        return R.ok().data(map);
    }
}
