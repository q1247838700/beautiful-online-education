package com.lyg.video;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import org.junit.jupiter.api.Test;

/**
 * @author lyg
 * @create 2020-04-04-16:37
 */
public class VodSdkTest {

    String accessKeyId = "LTAI4Ff7z5jZPJZTKzwNLRnL";
    String accessKeySecret = "MFgPC7WdBZ4Be03sluLWZM7mqz5noa";

    @Test
    public void testGetVideoPlayAuth() throws ClientException {

        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        try {

            //设置请求参数
            request.setVideoId("c8300dc65a374866a5bf64686c466836");
            //获取请求响应
            response = client.getAcsResponse(request);

            //输出请求结果
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }

        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
