package com.lyg.edu.service.feignservice;

import com.lyg.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author lyg
 * @create 2020-04-05-18:31
 */
@FeignClient(name = "video-edu")
public interface FeignVideoService {
    /**
     * feign的接口,删除一个视频
     *
     * @param videoId
     * @return
     */
    @DeleteMapping("/vod/video/{videoId}")
    R removeVideo(@PathVariable("videoId") String videoId);

    /**
     * 删除一堆视频
     * @param VideoIdList
     * @return
     */
    @DeleteMapping("/vod/video/delete")
    public R removeVideos( List<String> VideoIdList);

}

