package com.lyg.edu.service;

import com.lyg.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyg.edu.entity.query.ChapterDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lyg
 * @since 2020-04-01
 */
public interface ChapterService extends IService<Chapter> {


    List<ChapterDto> getChapterTree(String id);
}
