package com.lyg.handler;

import com.lyg.edu.common.EduException;
import com.lyg.edu.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyg
 * @create 2020-04-04-20:39
 */
@Slf4j
@ControllerAdvice //aop增强,统一处理
@ResponseBody
public class GlobalExceptionHandler {



    /**
     * 自定义异常类处理逻辑
     */
    @ExceptionHandler(EduException.class)
    public R eduError(EduException e) {
        //异常输出信息
        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMessage());

    }

}
