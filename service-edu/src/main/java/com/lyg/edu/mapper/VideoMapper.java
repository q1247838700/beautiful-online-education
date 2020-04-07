package com.lyg.edu.mapper;

import com.lyg.edu.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author lyg
 * @since 2020-04-01
 */
public interface VideoMapper extends BaseMapper<Video> {

    List<Video> getVideoByChapterIds(@Param("chapterListIds") List chapterListIds);
}
