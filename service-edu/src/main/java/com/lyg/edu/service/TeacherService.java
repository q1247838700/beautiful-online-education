package com.lyg.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyg.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyg.edu.entity.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lyg
 * @since 2020-03-03
 */
public interface TeacherService extends IService<Teacher> {

    void pageQUery(Page<Teacher> teacherPage, TeacherQuery teacher);
}
