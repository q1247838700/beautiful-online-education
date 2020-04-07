package com.lyg.edu.controller;


import com.lyg.edu.common.R;
import com.lyg.edu.service.UserCenterService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-04-06
 */
@RestController
@CrossOrigin
@RequestMapping("/edu/user/center")
public class UserCenterController {
    @Autowired
    private UserCenterService service;

    @ApiOperation(value = "今日注册数")
    @GetMapping(value = "/countRegister/{day}")
    public R registerCount(
            @ApiParam(name = "day", value = "统计日期")
            @PathVariable String day) {
        Integer count = service.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }

}

