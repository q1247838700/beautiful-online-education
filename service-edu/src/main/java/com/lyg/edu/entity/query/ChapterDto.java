package com.lyg.edu.entity.query;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**chapter的树包装类
 * @author lyg
 * @create 2020-04-01-17:58
 */
@Data
public class ChapterDto {
    private String id;
    private String title;

    private List<VideoDto> children=new ArrayList<VideoDto>();
}
