package com.lyg.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lyg.edu.common.R;
import com.lyg.service.VideoService;

import com.lyg.utils.AliyunVodSDKUtils;
import com.lyg.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lyg
 * @create 2020-04-04-15:37
 */
@CrossOrigin
@RestController
@RequestMapping("/vod/video")
@Api(tags = "阿里云视频管理接口")
public class VideoController {

    @Autowired
    VideoService service;

    @GetMapping("getAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {

        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

        //初始化
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);

        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);

        //得到播放凭证
        String playAuth = response.getPlayAuth();

        //返回结果
        return R.ok().message("获取凭证成功").data("playAuth", playAuth);
    }

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
