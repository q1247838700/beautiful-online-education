package com.lyg.edu.service.FeignService;

import com.lyg.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lyg
 * @create 2020-04-06-23:00
 */
@FeignClient(value = "edu-user-center")
public interface UserCenterService {
    /**
     * feign调用用户中心的远程调用
     * @param day
     * @return
     */
    @GetMapping(value = "/edu/user/center/countRegister/{day}")
    public R registerCount(@PathVariable("day") String day);

}
