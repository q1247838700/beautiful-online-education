package com.lyg.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyg.edu.common.R;
import com.lyg.edu.entity.Subject;
import com.lyg.edu.entity.query.SubjectTree;
import com.lyg.edu.mapper.SubjectMapper;
import com.lyg.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.Lock;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-03-09
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {


    @Override
    public R importSubjects(MultipartFile file) {
        HSSFWorkbook workbook = null;
        InputStream inputStream = null;
        List<String> list = new ArrayList<>();
        //1.创建大文件上传到这,然后填入数据库
        try {
            inputStream = file.getInputStream();
            workbook = new HSSFWorkbook(inputStream);
            //获取该sheet
            HSSFSheet sheet = workbook.getSheetAt(0);
            //获取总共有多少行
            int rowNum = sheet.getLastRowNum();
            //从第二行开始遍历
            for (int i = 1; i <= rowNum; i++) {
                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    //行为空
                    //TODO

                } else {
                    //行不为空
                    //1.调用方法查询该第一级有没有
                    String oneTitle = row.getCell(0).getStringCellValue();

                    if (oneTitle == null || "".equals(oneTitle)) {
                        list.add("第" + i + "行第2列没有数据");
                        continue;//跳出当前循环
                    }

                    Subject subject1 = getSubjectByTitle(oneTitle);
                    if (subject1 != null) {
                        //数据库已有,不进行操作
                        //TODO
                    } else {
                        //数据库没有数据,需要进行插入操作
                        Subject subjectOne = new Subject();
                        subjectOne.setTitle(oneTitle);
                        baseMapper.insert(subjectOne);
                        subject1 = subjectOne;
                    }
                    //获取二级列表
                    String twoTitle = row.getCell(1).getStringCellValue();

                    if (twoTitle == null || "".equals(twoTitle)) {
                        list.add("第" + i + "行第2列没有数据");
                        continue;//跳出当前循环
                    }

                    //调用方法查询数据库看是否有此二级功课
                    Subject subject2 = getSubjectByTitle(twoTitle);
                    if (subject2 != null) {
                        //数据库已有,不进行操作
                        //TODO
                    } else {
                        //插入数据库,插入title和parent_id
                        Subject subjectTwo = new Subject();
                        subjectTwo.setTitle(twoTitle);
                        subjectTwo.setParentId(subject1.getId());
                        baseMapper.insert(subjectTwo);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.error().message("服务器出现问题");

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (list.size() == 0) {
            return R.ok().message("导入成功");
        } else {
            return R.error().message("有空余的没有填写").data("emptyMessage", list);
        }

    }

//    @Override
//    public R getSubjects() {
//        //获取数据库parent_id全部为0的数据
//        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
//        wrapper.eq("parent_id", "0");
//        //从数据库查出数据
//        List subjectList = baseMapper.selectList(wrapper);
//        for (int i = 0; i < subjectList.size(); i++) {
//            //取出list每一个数据subject
//            Subject subject = (Subject) subjectList.get(i);
//            //新增subjectTree,将每一个subject转化成subjectTree
//            SubjectTree tree = new SubjectTree(subject.getId(), subject.getTitle());
//            subjectList.set(i, tree);
//            //根据每个一级分类的id查出二级分类
//            QueryWrapper<Subject> wrapper1 = new QueryWrapper<>();
//            wrapper1.eq("parent_id", tree.getId());
//            List childrens = baseMapper.selectList(wrapper1);
//            //遍历每一个取出来的一级节点
//            for (int j = 0; j < childrens.size(); j++) {
//                Subject subject1 = (Subject) childrens.get(j);
//                SubjectTree tree1 = new SubjectTree(subject1.getId(), subject1.getTitle());
//                childrens.set(j, tree1);
//            }
//            tree.setChildren(childrens);
//        }
//        //根据一级分类查询二级分类
//        return R.ok().data("subjects", subjectList);
//    }


    @Override
    public R getSubjects() {
        //获取数据库subject表的全部数据
        List<Subject> subjects = baseMapper.selectList(null);

        //获取全部的一级目录
        List<SubjectTree> oneSubjectTree = subjects.stream().filter(subject -> "0".equals(subject.getParentId())).map(subject -> new SubjectTree(subject.getId(), subject.getTitle())).collect(Collectors.toList());
        //获取全部的二级目录
        List<Subject> twoSubjects = subjects.stream().filter(subject -> !"0".equals(subject.getParentId())).collect(Collectors.toList());
        for (int i = 0; i < oneSubjectTree.size(); i++) {
            SubjectTree parent = oneSubjectTree.get(i);
            Iterator<Subject> iterator = twoSubjects.iterator();
            ArrayList<SubjectTree> children = new ArrayList<>();
            while (iterator.hasNext()) {
                Subject subject = iterator.next();
                //判断是否是他的子级目录
                if (parent.getId().equals(subject.getParentId() )) {
                    //是
                    SubjectTree tree = new SubjectTree(subject.getId(), subject.getTitle());
                    children.add(tree);
                    iterator.remove();
                }
            }
            parent.setChildren(children);
        }
        return R.ok().data("subjects",oneSubjectTree);
    }


    /**
     * 删除操作
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(String id) {
        //判断数据库里面有没有他的子功课,没有的话就可以删除
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Subject> list = baseMapper.selectList(wrapper);
        if (list.size()==0){
            //没有子功课,安心删除他
            baseMapper.deleteById(id);
            return true;
        }
        //有子功课,直接返回false
        return false;
    }

    @Override
    public boolean saveSubject(Subject subject) {
        if (subject.getParentId()==null||"".equals(subject.getParentId())){
            //执行一级添加操作
            subject.setParentId("0");
        }
        int insert = baseMapper.insert(subject);
        if (insert>0){
            return true;
        }
        return false;
    }


    /**
         * 一级查询
         *
         * @param title
         * @return
         */
        public Subject getSubjectByTitle (String title){
            QueryWrapper<Subject> wrapper = new QueryWrapper<>();
            wrapper.eq("title", title);
            Subject subject = baseMapper.selectOne(wrapper);
            return subject;
        }


    }
