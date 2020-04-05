package com.lyg.edu.entity;

import lombok.Data;

import java.math.BigDecimal;

/**这个实体类是为了显示科目最后的细节
 * @author lyg
 * @create 2020-04-03-15:57
 */
@Data
public class CourseDetails {

    private String id;
    /**
     * 课程名字
     */
    private String courseName;
    /**
     * 课程价钱
     */
    private BigDecimal price;
    /**
     * 课程封面url
     */

    private String url;
    /**
     * 课时
     */
    private Integer lessonNum;
    /**
     * 课程发布状态
     */
    private String status;
    /**
     * 课程的父级类目
     */
    private String parentSubject;
    /**
     * 课程的所属类目
     */
    private String subject;
    /**
     * 教师名字
     */
    private String teacherName;


}
