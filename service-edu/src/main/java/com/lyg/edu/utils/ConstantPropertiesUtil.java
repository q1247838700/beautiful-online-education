package com.lyg.edu.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lyg
 * @create 2020-03-06-20:41
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
//    aliyun.oss.file.endpoint=oss-cn-hangzhou.aliyuncs.com
//    aliyun.oss.file.keyid=LTAI4Ff7z5jZPJZTKzwNLRnL
//    aliyun.oss.file.keysecret=MFgPC7WdBZ4Be03sluLWZM7mqz5noa
//#bucket可以在控制台创建，也可以使用java代码创建
//    aliyun.oss.file.bucketname=edu-lyg

    @Value("${aliyun.oss.file.endpoint}")
    private String point;
    @Value("${aliyun.oss.file.keyid}")
    private String id;
    @Value("${aliyun.oss.file.keysecret}")
    private String secret;
    @Value("${aliyun.oss.file.bucketname}")
    private String buname;

    public static String ENDPOINT;
    public static String KEYID;
    public static String KEYSECRET;
    public static String BUCKETNAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT=point;
        KEYID=id;
        KEYSECRET=secret;
        BUCKETNAME=buname;
    }


}
