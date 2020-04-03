package com.lyg.edu.service;

import com.lyg.edu.common.R;
import com.lyg.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
