package com.lyg.edu.controller;

import com.lyg.edu.common.R;
import com.lyg.edu.utils.UploadUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

/**
 * @author lyg
 * @create 2020-03-06-18:37
 */
@ApiModel("文件上传,其实就是上传头像")
@RestController
@CrossOrigin
@RequestMapping("/edu/teacher/oss")
public class UploadFileController {
    @ApiOperation("文件上传方法")
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) {
        UploadUtil util = new UploadUtil();
        String url = null;
        try {
            url = util.update(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok().data("url", url);
    }
}
