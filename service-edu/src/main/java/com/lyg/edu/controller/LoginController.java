package com.lyg.edu.controller;

import com.lyg.edu.common.R;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyg
 * @create 2020-03-05-15:29
 */
@RestController
@CrossOrigin
@RequestMapping("/edu/teacher")
public class LoginController {
    @PostMapping("/login")
    public R login(){
       // {"code":20000,"data":{"token":"admin"}}
        return R.ok().data("token", "admin");
    }
    @GetMapping("/info")
    public R info(){
        // {"code":20000,
        // "data":
        // {"roles":["admin"],
        // "name":"admin",
        // "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
        List<String> list =new ArrayList<>();
        list.add("admin");
        return R.ok().data("roles", list).data("name", "admin")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
