package com.lyg.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyg.edu.entity.Chapter;
import com.lyg.edu.entity.Video;
import com.lyg.edu.entity.query.ChapterDto;
import com.lyg.edu.entity.query.VideoDto;
import com.lyg.edu.exception.EduException;
import com.lyg.edu.mapper.ChapterMapper;
import com.lyg.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyg.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-04-01
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private VideoService service;

    /**
     * 根据id将chapter和video中的全部求出来,然后构建成一颗树发给前台
     *
     * @param id
     * @return
     */
    @Override
    public List<ChapterDto> getChapterTree(String id) {
        //1.根据id来求出所有的章节
        if (StringUtils.isEmpty(id)) {
            throw new EduException(20001, "该id为空");
        }
        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", id);
        List<Chapter> chapters = baseMapper.selectList(chapterWrapper);
        //2根据id来求出所有的video
        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", id);
        List<Video> videoList = service.list(videoWrapper);
        //3.弄成树状结构给前台发过去
        List<ChapterDto> list = new ArrayList<>();
        for (int i = 0; i < chapters.size(); i++) {
            Chapter chapter = chapters.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            //通过stream流来处理list,贼爽
            List<VideoDto> videoDtos = videoList.stream().filter(v -> v.getChapterId().equals(chapterDto.getId()) )
                    .map(v -> {
                        VideoDto videoDto = new VideoDto();
                        BeanUtils.copyProperties(v, videoDto);
                        return videoDto;
                    }).collect(Collectors.toList());
            chapterDto.setChildren(videoDtos);
            list.add(chapterDto);
        }
        return list;
    }

    /**
     * 判断该chapter下是否有video,若是有的话就不让删除
     * @param id
     * @return
     */
    @Override
    public boolean removeByIdIfExistVideo(String id) {
        //根据id查询video数据有多少个
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
       wrapper.eq("chapter_id", id);
        int count = service.count(wrapper);
        if (count==0){
            //此时执行删除操作
            baseMapper.deleteById(id);
        }
        return count==0;
    }
}
