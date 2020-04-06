package com.lyg.edu.service;

import com.lyg.edu.common.R;
import com.lyg.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author lyg
 * @since 2020-04-01
 */
public interface VideoService extends IService<Video> {

    R saveVideo(Video video);

    R updateVideo(Video video);

    boolean removeVideoById(String id);

    /**
     * 通过传过来的chapter章节id来找出来各个的video
     * @param chapterListIds
     * @return
     */
    List<Video> getVideosByIds( List chapterListIds);
}
