package com.lyg.edu.entity.query;

import lombok.Data;

/**
 * Video的树包装类,出来树形表示并没有什么用
 *
 * @author lyg
 * @create 2020-04-01-17:59
 */
@Data
public class VideoDto {
    /**
     * videoid
     */
    private String id;
    /**
     * 标题
     */
    private String title;
}
