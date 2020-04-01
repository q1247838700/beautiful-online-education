package com.lyg.edu.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lyg
 * @create 2020-03-22-20:33
 */
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
@Data
public class CourseWrapper {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String parentSubjectId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;
}
