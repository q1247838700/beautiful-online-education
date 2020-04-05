package com.lyg.video;


import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.*;
import com.aliyun.vod.upload.resp.*;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.InputStream;
import java.util.List;


/**
 * @author lyg
 * @create 2020-04-04-16:13
 */
public class UploadVideoDemo {
    //账号AK信息请填写(必选)
    private static final String accessKeyId = "LTAI4Ff7z5jZPJZTKzwNLRnL";
    //账号AK信息请填写(必选)
    private static final String accessKeySecret = "MFgPC7WdBZ4Be03sluLWZM7mqz5noa";

    public static void main(String[] args) {
        // 一、视频文件上传
        // 视频标题(必选)
        String title = "测试一下";
        // 3.流式上传时，文件名称为源文件名，如文件名称.mp4(必选)。
        // 任何上传方式文件名必须包含扩展名
        String fileName = "C:\\Users\\Administrator\\Documents\\My Video\\01-测试视频.mp4";

        // 4.流式上传，如文件流和网络流
        InputStream inputStream = null;
        // 4.1 文件流
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        testUploadStream(accessKeyId, accessKeySecret, title, fileName, inputStream);


    }





    /**
     * 流式上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     * @param inputStream
     */
    private static void testUploadStream(String accessKeyId, String accessKeySecret, String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * 根据id获得凭证auth
     * @throws ClientException
     */
    @Test
    public void test01() throws ClientException {
        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        try {

            //设置请求参数
            request.setVideoId("d1ad6c877b2247178bd5a6627ac1d46b");
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
    /**
     * 获取视频播放地址
     * @throws ClientException
     */
    @Test
    public void testGetPlayInfo() throws ClientException {

        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        try {

            //设置请求参数
            //注意：这里只能获取非加密视频的播放地址
            request.setVideoId("d1ad6c877b2247178bd5a6627ac1d46b");
            //获取请求响应
            response = client.getAcsResponse(request);

            //输出请求结果
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }

        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
