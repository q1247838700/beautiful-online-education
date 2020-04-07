package com.lyg.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyg.edu.common.R;
import com.lyg.edu.entity.Teacher;
import com.lyg.edu.entity.query.TeacherQuery;
import com.lyg.edu.service.TeacherService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-03-03
 */
@CrossOrigin
@ApiModel(value = "操作教师的控制类")
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {
    @Autowired
    private TeacherService service;

    /**
     * 获取所有教师
     *
     * @return
     */
    @ApiOperation(value = "获取所有教师")
    @GetMapping("/all")
    public R getAllTeacher() {
        List<Teacher> teachers = service.list(null);
        return R.ok().data("items", teachers);
    }

    /**
     * 通过id查询教师
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "通过id查询教师")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") String id) {

        Teacher teacher = service.getById(id);
        return R.ok().data("teacher", teacher);

    }

    /**
     * 通过id修改教师
     */
    @ApiOperation(value = "通过id修改教师")
    @PutMapping("/{id}")
    public R updateTeacherById(@PathVariable String id,@RequestBody Teacher teacher) {
        boolean update = service.updateById(teacher);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    /**
     * 新增教师
     *
     * @param teacher
     * @return
     */
    @ApiOperation(value = "新增教师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody Teacher teacher) {
        boolean b = service.save(teacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据Id逻辑删除教师
     *
     * @param id
     * @return
     */
    @ApiOperation(value ="根据Id删除教师" )
    @DeleteMapping("{id}")
    public R removeTeacherById(@PathVariable("id") String id) {
        boolean b = service.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 开启一般分页
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation("分页查询展示")
    @GetMapping("/{page}/{limit}")
    public R PageList(@PathVariable("page") Long page,
                      @PathVariable("limit") Long limit) {
        Page<Teacher> teacherPage = new Page<>(page, limit);
        service.page(teacherPage, null);
        List<Teacher> teachers = teacherPage.getRecords();//教师列表
        long total = teacherPage.getTotal(); //总记录数
        return R.ok().data("teachers", teachers).data("total", total);
    }

    /**
     * 开启一般分页
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation("带条件的分页展示")
    @PostMapping("/query/{page}/{limit}")
    public R pageQuery(@PathVariable("page") Long page,
                       @PathVariable("limit") Long limit, @RequestBody(required = false) TeacherQuery teacher) {
        Page<Teacher> teacherPage = new Page<>(page, limit);

        service.pageQUery(teacherPage, teacher);
        List<Teacher> teachers = teacherPage.getRecords();//教师列表
        long total = teacherPage.getTotal(); //总记录数
        return R.ok().data("teachers", teachers).data("total", total);
    }


}

