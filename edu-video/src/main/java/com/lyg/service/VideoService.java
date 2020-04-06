package com.lyg.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lyg
 * @create 2020-04-04-20:29
 */
public interface VideoService {
    /**
     * 上传视频到阿里云的接口
     * @param file
     * @return
     */
    String uploadVideo(MultipartFile file);

    /**
     * 删除视频接口
     * @param videoId
     */
    void removeVideo(String videoId);

    void removeManyVideo(List<String> videoIdList);
}
