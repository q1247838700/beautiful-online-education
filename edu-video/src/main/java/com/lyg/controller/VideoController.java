package com.lyg.controller;

import com.lyg.edu.common.R;
import com.lyg.service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lyg
 * @create 2020-04-04-15:37
 */
@CrossOrigin
@RestController()
@RequestMapping("/vod/video")
public class VideoController {

    @Autowired
    VideoService service;

    @PostMapping("/uploadVideo")
    public R uploadVideo(@RequestParam("file") MultipartFile file) {
        String videoId = service.uploadVideo(file);
        int i = file.getOriginalFilename().lastIndexOf(".");
        String title = file.getOriginalFilename().substring(0, i);
        if (videoId != null) {
            return R.ok().data("videoId", videoId).data("title", title);

        } else {
            return R.error().message("上传失败");
        }
    }

    @DeleteMapping("/{videoId}")
    public R removeVideo(@PathVariable String videoId) {
        service.removeVideo(videoId);
        return R.ok().message("删除视频成功");
    }
    @DeleteMapping("/delete")
    public R removeVideos(@RequestBody List<String> VideoIdList) {
        service.removeManyVideo(VideoIdList);
        return R.ok().message("删除成功");
    }

}
