package com.lyg.edu.service.impl;

import com.lyg.edu.entity.Video;
import com.lyg.edu.mapper.VideoMapper;
import com.lyg.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-04-01
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

}
