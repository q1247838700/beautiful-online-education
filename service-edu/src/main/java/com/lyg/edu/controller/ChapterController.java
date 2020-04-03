package com.lyg.edu.controller;


import com.lyg.edu.common.R;
import com.lyg.edu.entity.Chapter;
import com.lyg.edu.entity.query.ChapterDto;
import com.lyg.edu.service.ChapterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lyg
 * @since 2020-04-01
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService service;
@ApiOperation("根据id返回章节树")
@GetMapping("/chapterTree/{id}")
    public R getChapterTree(@PathVariable("id") String id){
       List<ChapterDto> list= service.getChapterTree(id);
       return R.ok().data("tree", list);
    }







    /**
     * 通过课程id来获取id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getChapterById(@PathVariable("id") String id) {

        Chapter chapter = service.getById(id);
        if (chapter != null) {
            return R.ok().data("item", chapter);
        } else {
            return R.error().message("没有该章节");
        }
    }

    /**
     * 保存章节
     *
     * @param chapter
     * @return
     */
    @PostMapping()
    public R addChapter(@RequestBody Chapter chapter) {

        boolean save = service.save(chapter);
        if (save) {
            return R.ok().data("item", chapter);
        } else {
            return R.error().message("出错了");
        }
    }

    @DeleteMapping("/deleteChapter/{id}")
    public R deleteChapterById(@PathVariable("id") String id) {
        boolean b = service.removeByIdIfExistVideo(id);
        if (b) {
            return R.ok();
        } else {
            return R.error().message("该分类下有子节点,不能进行删除");
        }
    }

    @PutMapping()
    public R updateChapterById(@RequestBody Chapter chapter) {


        boolean byId = service.updateById(chapter);
        if (byId) {
            return R.ok().data("item", chapter);
        } else {
            return R.error();
        }


    }

}

