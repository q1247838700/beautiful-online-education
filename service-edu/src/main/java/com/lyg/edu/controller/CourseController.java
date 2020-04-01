package com.lyg.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyg.edu.common.R;
import com.lyg.edu.entity.Course;
import com.lyg.edu.entity.query.CourseQuery;
import com.lyg.edu.entity.query.CourseWrapper;
import com.lyg.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("删除课程")
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable("id") String id) {
        return service.deleteCourseById(id);
    }

    @ApiOperation("修改课程")
    @PutMapping("")
    public R updateCourseById(@RequestBody CourseQuery courseQuery) {
        return service.updateCourseById(courseQuery);

    }


}

