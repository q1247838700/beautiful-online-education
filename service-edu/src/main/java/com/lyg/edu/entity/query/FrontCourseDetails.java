package com.lyg.edu.entity.query;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lyg
 * @create 2020-04-07-20:05
 */
@Data
public class FrontCourseDetails {
    /**
     * 课程Id
     */
    private String id;
    /**
     * 课程价格
     */
    private BigDecimal price;
    /**
     * 课程名字
     */
    private String title;
    /**
     * 课程父级subject
     */
    private String parTitle;
    /**
     * 直接subject
     */
    private String subTitle;
    /**
     * 课程封面
     */
    private String cover;
    /**
     *课程购买量
     */
    private Integer buyCount;
    /**
     * 总课时
     */
    private Integer lessonNum;
    /**
     * 总观看次数
     */
    private Integer viewCount;
    /**
     * 课程描述
     */
    private String description;
    /**
     * 教师id
     */
    private String teacherId;
    /**
     * 教师名字
     */
    private String teacherName;
    /**
     * 教师简介
     */
    private String intro;

}
