package com.lyg.edu.service.impl;

import com.lyg.edu.common.R;
import com.lyg.edu.entity.Video;
import com.lyg.edu.mapper.VideoMapper;

import com.lyg.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-04-01
 */
@Service
@Transactional
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {


    @Override
    public R saveVideo(Video video) {

        video.setId(null);
        baseMapper.insert(video);
        return R.ok().data("video", video);
    }

    @Override
    public R updateVideo(Video video) {
        if (video.getId() == null || "".equals(video.getId())) {
            return R.error().message("删除失败,缺少必要条件");
        }
        baseMapper.updateById(video);
        return R.ok().data("video", video);
    }

    @Override
    public boolean removeVideoById(String id) {

        int i = baseMapper.deleteById(id);

        return i>0;
    }

    /**
     * 通过章节id来找出来video
     * @param chapterListIds
     * @return
     */
    @Override
    public List<Video> getVideosByIds(List chapterListIds) {

     List<Video> videos=   baseMapper.getVideoByChapterIds(chapterListIds);
        return videos;
    }
}
