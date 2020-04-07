package com.lyg.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyg.edu.common.R;
import com.lyg.edu.entity.Teacher;
import com.lyg.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author lyg
 * @create 2020-04-07-15:55
 */
@CrossOrigin
@RestController
@RequestMapping("/edu/front/teacher")
@Api(tags = "前端教师的控制类")
public class FrontTeacherController {
    @Autowired
    private TeacherService service;


    @ApiOperation(value = "通过id查询教师")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") String id) {

        Map<String, Object> map = service.getTeacherAndCourseById(id);
        //map的key为  teacher和 courses
        return R.ok().data(map);

    }

    @ApiOperation(("分页教师列表"))
    @GetMapping("/{page}/{limit}")
    public R getTeacherByPage(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
        Page<Teacher> page1 = new Page<>(page, limit);
        service.pageQUery(page1, null);

        return R.ok().data("pageWrapper", page1);
    }
}
