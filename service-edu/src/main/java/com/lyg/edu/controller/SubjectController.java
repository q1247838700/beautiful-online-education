package com.lyg.edu.controller;


import com.lyg.edu.common.R;
import com.lyg.edu.entity.Subject;
import com.lyg.edu.service.SubjectService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-03-09
 */
@ApiModel("课程列表")
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class SubjectController {
    @Autowired
    private SubjectService service;


    @ApiOperation("删除功课")
    @DeleteMapping("{id}")
    public R deleteSubject(@PathVariable("id") String id) {
        boolean flag = service.deleteById(id);
        if (flag) {
            //删除成功
            return R.ok();
        } else {

            return R.error();
        }


    }

    @ApiOperation("增加功课")
    @PostMapping("/addSubject")
    public R insertSubject(@RequestBody Subject subject) {

        System.out.println(subject);
        boolean save = service.saveSubject(subject);
        if (save) {
            return R.ok().message("添加成功");
        }
        return R.error().message("添加失败");
    }

    @ApiOperation("功课列表")
    @GetMapping("/getSubjects")
    public R getSubject() {
        R result = service.getSubjects();
        return result;
    }

    @ApiOperation("上传excel文件到数据库")
    @PostMapping(value = "/import")
    public R importSubjects(@RequestParam("file") MultipartFile file) {
        R r = service.importSubjects(file);
        return r;
    }
}

