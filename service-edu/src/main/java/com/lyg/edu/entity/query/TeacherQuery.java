package com.lyg.edu.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lyg
 * @create 2020-03-04-1:12
 */
@Data
@ApiModel(value = "用来传递根据条件查找的数据")
public class TeacherQuery {
    @ApiModelProperty(value = "教师名字")
    private String name;
    @ApiModelProperty(value = "教师等级")
    private String level;
    @ApiModelProperty(value = "开始时间")
    private String begin;
    @ApiModelProperty(value = "结束时间")
    private String end;

}
