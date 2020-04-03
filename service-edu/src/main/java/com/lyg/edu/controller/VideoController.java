package com.lyg.edu.controller;


import com.lyg.edu.common.R;
import com.lyg.edu.entity.Video;
import com.lyg.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Severity;

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
        boolean b = service.removeById(id);
        if (b) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("删除失败");
        }

    }

    /**
     * 修改
     * @param video
     * @return
     */
    @PutMapping("/updateVideo")
    public R updateVideo(@RequestBody Video video) {
        return service.updateVideo(video);
    }

    /**
     * 查
     */
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

