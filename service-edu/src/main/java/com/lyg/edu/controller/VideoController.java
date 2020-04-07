package com.lyg.edu.controller;


import com.lyg.edu.common.R;
import com.lyg.edu.entity.Video;
import com.lyg.edu.service.VideoService;
import com.lyg.edu.service.feignservice.FeignVideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-04-01
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService service;


    @Autowired
    private FeignVideoService feignVideoService;

    /**
     * 增
     *
     * @param video
     * @return
     */
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody Video video) {
        return service.saveVideo(video);
    }

    /**
     * 删
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R removeVideo(@PathVariable String id) {
        Video video = service.getById(id);
        String videoSourceId = video.getVideoSourceId();
        //调用feign来删除
        R video1 = feignVideoService.removeVideo(videoSourceId);
        System.out.println(video1.getMessage());
        boolean flag = service.removeVideoById(id);
        return R.ok().message("成功");


    }

    /**
     * 修改
     *
     * @param video
     * @return
     */
    @PutMapping("/updateVideo")
    public R updateVideo(@RequestBody Video video) {
        return service.updateVideo(video);
    }


    @GetMapping("/{id}")
    public R getVideo(@PathVariable String id) {
        Video video = service.getById(id);
        if (video != null) {
            return R.ok().data("video", video);
        } else {
            return R.error().message("为空");
        }
    }

}

