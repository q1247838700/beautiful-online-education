package com.lyg.edu.service;

import com.lyg.edu.common.R;
import com.lyg.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lyg
 * @since 2020-03-09
 */
public interface SubjectService extends IService<Subject> {

    R importSubjects(MultipartFile file);

    R getSubjects();

    boolean deleteById(String id);


    boolean saveSubject(Subject subject);
}
