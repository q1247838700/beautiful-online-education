package com.lyg.service.FeignService;

import com.lyg.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lyg
 * @create 2020-04-05-22:16
 */

@FeignClient(name = "service-edu")
public interface FeignTeacherService {
    @GetMapping("/edu/teacher/{id}")
    public R getById(@PathVariable("id") String id);

}

