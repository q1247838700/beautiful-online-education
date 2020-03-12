package com.lyg.edu.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author lyg
 * @create 2020-03-06-20:47
 */
public class UploadUtil {

    public String  update(MultipartFile file) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(ConstantPropertiesUtil.ENDPOINT, ConstantPropertiesUtil.KEYID, ConstantPropertiesUtil.KEYSECRET);
        InputStream inputStream = file.getInputStream();
        String name = file.getOriginalFilename();//获取文件名
        String uuid = UUID.randomUUID().toString();
        String filePath=DateTime.now().toString("yyyy/MM/dd");
         name=filePath+"/"+uuid+name;

        //https://edu-lyg.oss-cn-hangzhou.aliyuncs.com/pig.png
// 上传文件流。

        ossClient.putObject(ConstantPropertiesUtil.BUCKETNAME, name, inputStream);

// 关闭OSSClient。
        ossClient.shutdown();

        return "https://"+ConstantPropertiesUtil.BUCKETNAME+"."+ConstantPropertiesUtil.ENDPOINT+"/"+name;
    }
}
