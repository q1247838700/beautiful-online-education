package com.lyg.edu.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyg.edu.entity.CourseDetails;
import com.lyg.edu.entity.query.ChapterDto;
import com.lyg.edu.entity.query.CourseWrapper;
import com.lyg.edu.entity.query.FrontCourseDetails;
import com.lyg.edu.service.CourseService;
import com.lyg.edu.service.VideoService;
import com.lyg.edu.service.feignservice.FeignVideoService;
import com.lyg.edu.common.R;
import com.lyg.edu.entity.Course;
import com.lyg.edu.entity.CourseDescription;
import com.lyg.edu.entity.Video;
import com.lyg.edu.entity.query.CourseQuery;
import com.lyg.edu.mapper.CourseMapper;
import com.lyg.edu.service.ChapterService;
import com.lyg.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-03-13
 */
@Service
@Transactional
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    //课程细节service
    private CourseDescriptionService descriptionService;
    @Autowired
    //远程调用feignservice
    private FeignVideoService feignVideoService;
    @Autowired
    //video的service
    private VideoService videoService;
    @Autowired
    private ChapterService chapterService;

    /**
     * 有条件的分页查询,判断各个查询条件
     *
     * @param pageParam     page插件
     * @param courseWrapper 一个包装实体类
     */
    @Override
    public void pageQuery(Page<Course> pageParam, CourseWrapper courseWrapper) {


        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseWrapper == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseWrapper.getTitle();
        String teacherId = courseWrapper.getTeacherId();
        String parentSubjectId = courseWrapper.getParentSubjectId();
        String subjectId = courseWrapper.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(parentSubjectId)) {
            queryWrapper.eq("parent_subject_id", parentSubjectId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }


    /**
     * 保存数据
     *
     * @param query
     * @return
     */
    @Override
    @SentinelResource(value = "saveCourse")
    public R saveCourse(CourseQuery query) {
        if (query == null) {
            return R.error().message("没有数据");
        }
        //保存course
        Course course = new Course();
        BeanUtils.copyProperties(query, course);
        baseMapper.insert(course);
        BeanUtils.copyProperties(course, query);


        //将courseDescription保存起来
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(query.getDescription());
        descriptionService.save(courseDescription);

        return R.ok().data("course", query);

    }

    @Override
    @SentinelResource(value = "getCourseById")
    public R getCourseById(String id) {

        CourseQuery query = new CourseQuery();

        Course course = baseMapper.selectById(id);

        BeanUtils.copyProperties(course, query);

        CourseDescription description = descriptionService.getById(id);
        if (description != null) {
            BeanUtils.copyProperties(description, query);
        }

        return R.ok().data("course", query);

    }

    @Override
    @SentinelResource(value = "deleteCourseById")
    public R deleteCourseById(String id) {

        //1.删除数据库course表该信息
        int i = baseMapper.deleteById(id);
        //2.删除数据库courseDescription表该信息
        boolean flag = descriptionService.removeById(id);
        if (i > 0 && flag == true) {
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");

    }

    /**
     * 修改id
     *
     * @param query
     * @return
     */
    @Override
    public R updateCourseById(CourseQuery query) {
        if (query == null) {
            return R.error().message("没有数据");
        }
        String description = query.getDescription();


        //判断描述是否为空,

        //为空,修改course即可
        Course course = new Course();
        BeanUtils.copyProperties(query, course);
        baseMapper.updateById(course);

        //将courseDescription保存起来
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(query.getId());
        courseDescription.setDescription(description);
        descriptionService.updateById(courseDescription);

        return R.ok().data("course", query);
    }

    @Override
    public CourseDetails getCourseDetails(String id) {
        CourseDetails details = baseMapper.getCourseDetails(id);
        return details;
    }

    @Override
    /**
     * 这是一个超级垃圾的代码,可优化度非常高,首先,批量删除阿里云视频可以放到一个消息中间件中去处理,其次,好多删除请求其实也是可以延迟删除的
     */
    public R deleteCourse(String id) {
        //先查出来chapter的各个id
        List<String> chapterIdList = baseMapper.getChapterId(id);
        if (chapterIdList.size() != 0) {
            //在查出来Video
            List<Video> videos = videoService.getVideosByIds(chapterIdList);
            if (videos.size() != 0) {
                //通过VideoSourceId去阿里云删除各个视频
                List<String> videoSourceIds = videos.stream().map(v -> v.getVideoSourceId()).collect(Collectors.toList());
                //批量删除阿里云视频
                feignVideoService.removeVideos(videoSourceIds);
                //通过edu_video的id来删除edu_video
                List<String> videoIds = videos.stream().map(v -> v.getId()).collect(Collectors.toList());
                videoService.removeByIds(videoIds);
            }



            //批量删除edu_chapter的值
            chapterService.removeByIds(chapterIdList);
        }

        //通过course的Id来删除edu_course_description
        videoService.removeVideoById(id);
        //删除该course的值
        baseMapper.deleteById(id);
        return R.ok().message("删除课程成功");
    }

    @Override
    public Map<String, Object> getCourseDetailsById(String id) {
        //求出来该课程的各个描述
        FrontCourseDetails details = baseMapper.getCourseDetailsById(id);
        //求出来该课程的章节和视频目录
        List<ChapterDto> chapterTree = chapterService.getChapterTree(id);
        Map<String, Object> map = new HashMap<>();
        map.put("details", details);
        map.put("chapterTree", chapterTree);
        return map;


    }

    /**
     * 前端查询课程列表,要求查出所有已发布的
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Page<Course> getCourseFront(Integer page, Integer limit) {

        //page插件
        Page<Course> page1 = new Page<>(page, limit);
        //包装wrapper条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("status", "true");
        baseMapper.selectPage(page1, wrapper);
        return page1;
    }


}
