package com.lyg.edu.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lyg
 * @create 2020-03-03-23:56
 */
@ApiModel(value = "返回值统一格式")
@Data
public class R {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回状态码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    private R() {

    }

    public static R ok() {
        R r = new R();
        return r.success(true)
                .code(Resource.SUCCESS)
                .message("操作成功");

    }
    public static R error(){
        R r = new R();
        return r.message("错误")
                .code(Resource.ERROR)
                .success(false);
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}
