package com.lyg.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lyg
 * @create 2020-04-04-16:23
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${aliyun.oss.file.ACCESS_KEY_ID}")
    private String id;
    @Value("${aliyun.oss.file.ACCESS_KEY_SECRET}")
    private String secret;


    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID=id;
        ACCESS_KEY_SECRET=secret;

    }
}
