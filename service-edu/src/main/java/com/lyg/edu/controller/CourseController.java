package com.lyg.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyg.edu.common.R;
import com.lyg.edu.entity.Course;
import com.lyg.edu.entity.CourseDetails;
import com.lyg.edu.entity.query.CourseQuery;
import com.lyg.edu.entity.query.CourseWrapper;
import com.lyg.edu.service.CourseService;
import com.lyg.edu.service.feignservice.FeignVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/edu/course")
@ApiModel("课程")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService service;
    @Autowired
    private FeignVideoService feignVideoService;

    /**
     * 因为和teacher类里面的分页查找冲突,所以没有直接敲,
     * 直接复制粘贴的,就是实现了如果没有传过来查询条件的的话,就是一个普通的分页查询
     * 如果传过来的PageWrapper有值的话就是一个条件查询
     *
     * @param page
     * @param limit
     * @param courseWrapper
     * @return
     */
    @ApiOperation(value = "分页课程列表")
    @PostMapping("/{page}/{limit}")
    public R pageQuery(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody CourseWrapper courseWrapper) {
        //分页插件获取
        Page<Course> coursePage = new Page<>(page, limit);

        service.pageQuery(coursePage, courseWrapper);
        List<Course> records = coursePage.getRecords();
        //获取总数量
        long total = coursePage.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 发布该课程
     * @param id
     * @return
     */
    @PutMapping("publish/{id}")
    public R publishCourse(@PathVariable String id) {

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        Course course = new Course();
        course.setId(id);
        course.setStatus("true");
        boolean b = service.updateById(course);
        if (b) {
            return R.ok().message("发布成功");
        } else {
            return R.error().message("发布失败");
        }
    }
    /**
     * 取消发布该课程
     * @param id
     * @return
     */
    @PutMapping("cancel/{id}")
    public R cancelCourse(@PathVariable String id) {

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        Course course = new Course();
        course.setId(id);
        course.setStatus("Draft");
        boolean b = service.updateById(course);
        if (b) {
            return R.ok().message("取消成功");
        } else {
            return R.error().message("取消失败");
        }
    }

    /**
     * 获取该课程细节
     *
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    public R getCourseDetails(@PathVariable String id) {
        CourseDetails details = service.getCourseDetails(id);
        if (details == null) {
            return R.error().message("没有该课程");
        } else {
            return R.ok().data("item", details);
        }
    }


    /**
     * 保存课程的方法,要不保存course,要不保存CourseDescription,二选一
     *
     * @param query
     * @return
     */
    @ApiOperation("保存课程")
    @PostMapping("/save")
    public R saveCourse(@RequestBody CourseQuery query) {
        //保存课程
        return service.saveCourse(query);
    }

    /**
     * 通过ID获取course和courseDescription
     *
     * @param id
     * @return
     */
    @ApiOperation("查看课程")
    @GetMapping("/{id}")
    public R getCourseById(@PathVariable("id") String id) {
        return service.getCourseById(id);
    }

    /**
     * 通过id删除该课时
     * @param id
     * @return
     */
    @ApiOperation("删除课程")
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable("id") String id) {

        return service.deleteCourse(id);
    }

    @ApiOperation("修改课程")
    @PutMapping("")
    public R updateCourseById(@RequestBody CourseQuery courseQuery) {
        return service.updateCourseById(courseQuery);

    }


}

