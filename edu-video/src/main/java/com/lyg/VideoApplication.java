package com.lyg;

import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author lyg
 * @create 2020-04-04-19:44
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class VideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class, args);
    }
}
