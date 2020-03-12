package com.lyg.edu.entity.query;

import com.lyg.edu.service.SubjectService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyg
 * @create 2020-03-10-21:08
 */


public class SubjectTree {
    private String id;
    private String title;
    private List<SubjectTree> children = new ArrayList<>();

    @Override
    public String toString() {
        return "SubjectTree{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", children=" + children +
                '}';
    }

    public SubjectTree(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public SubjectTree(String id, String title, List<SubjectTree> children) {
        this.id = id;
        this.title = title;
        this.children = children;
    }

    public SubjectTree() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectTree> getChildren() {
        return children;
    }

    public void setChildren(List<SubjectTree> children) {
        this.children = children;
    }
}
