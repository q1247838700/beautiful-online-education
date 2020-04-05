package com.lyg.edu.Handler;


import com.lyg.edu.common.EduException;
import com.lyg.edu.common.R;

import com.lyg.edu.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyg
 * @create 2020-03-04-13:18
 */
@Slf4j
@ControllerAdvice //aop增强,统一处理
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 数学错误异常(实验 用)
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    public R mathError(ArithmeticException e) {
        log.error(ExceptionUtil.getMessage(e)); //异常输出信息
        return R.error().message("数学错误异常");
    }

    /**
     * 服务器错误总体处理方法
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R globalError(Exception e) {
        return R.error().message("服务器错误");
    }

    /**
     * 自定义异常类处理逻辑
     */
    @ExceptionHandler(EduException.class)
    public R eduError(EduException e) {
        log.error(e.getMessage()); //异常输出信息
        return R.error().code(e.getCode()).message(e.getMessage());

    }
}
